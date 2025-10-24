package com.sap.ai.sdk.foundationmodels.openai.spring;

import static org.springframework.ai.model.tool.ToolCallingChatOptions.isInternalToolExecutionEnabled;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionDelta;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionRequest;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiChatCompletionResponse;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiClient;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiMessage;
import com.sap.ai.sdk.foundationmodels.openai.OpenAiToolCall;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionMessageToolCall;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.ChatCompletionTool;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.CreateChatCompletionResponseChoicesInner;
import com.sap.ai.sdk.foundationmodels.openai.generated.model.FunctionObject;
import io.vavr.control.Option;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.annotation.Nonnull;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.AssistantMessage.ToolCall;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.DefaultToolCallingManager;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import reactor.core.publisher.Flux;

/**
 * OpenAI Chat Model implementation that interacts with the OpenAI API to generate chat completions.
 */
@Slf4j
@RequiredArgsConstructor
public class OpenAiChatModel implements ChatModel {

    private final OpenAiClient client;

    @Nonnull
    private final DefaultToolCallingManager toolCallingManager =
            DefaultToolCallingManager.builder().build();

    @Override
    @Nonnull
    public ChatResponse call(@Nonnull final Prompt prompt) {
        val options = prompt.getOptions();
        var request = new OpenAiChatCompletionRequest(extractMessages(prompt));

        if (options != null) {
            request = extractOptions(request, options);
        }
        if ((options instanceof ToolCallingChatOptions toolOptions)) {
            request = request.withTools(extractTools(toolOptions));
        }

        val result = client.chatCompletion(request);
        val response = new ChatResponse(toGenerations(result));

        if (options != null && isInternalToolExecutionEnabled(options) && response.hasToolCalls()) {
            val toolExecutionResult = toolCallingManager.executeToolCalls(prompt, response);
            // Send the tool execution result back to the model.
            log.info("Re-invoking model with tool execution results.");
            return call(new Prompt(toolExecutionResult.conversationHistory(), options));
        }
        return response;
    }

    @Override
    @Nonnull
    public Flux<ChatResponse> stream(@Nonnull final Prompt prompt) {
        val options = prompt.getOptions();
        var request = new OpenAiChatCompletionRequest(extractMessages(prompt));

        if (options != null) {
            request = extractOptions(request, options);
        }
        if ((options instanceof ToolCallingChatOptions toolOptions)) {
            request = request.withTools(extractTools(toolOptions));
        }

        val stream = client.streamChatCompletionDeltas(request);
        final Flux<OpenAiChatCompletionDelta> flux =
                Flux.generate(
                        stream::iterator,
                        (iterator, sink) -> {
                            if (iterator.hasNext()) {
                                sink.next(iterator.next());
                            } else {
                                sink.complete();
                            }
                            return iterator;
                        });
        return flux.map(
                delta -> {
                    val assistantMessage = new AssistantMessage(delta.getDeltaContent(), Map.of());
                    val metadata =
                            ChatGenerationMetadata.builder().finishReason(delta.getFinishReason()).build();
                    return new ChatResponse(List.of(new Generation(assistantMessage, metadata)));
                });
    }

    private static List<OpenAiMessage> extractMessages(final Prompt prompt) {
        final List<OpenAiMessage> result = new ArrayList<>();
        for (final Message message : prompt.getInstructions()) {
            switch (message.getMessageType()) {
                case USER -> Option.of(message.getText()).peek(t -> result.add(OpenAiMessage.user(t)));
                case SYSTEM -> Option.of(message.getText()).peek(t -> result.add(OpenAiMessage.system(t)));
                case ASSISTANT -> addAssistantMessage(result, (AssistantMessage) message);
                case TOOL -> addToolMessages(result, (ToolResponseMessage) message);
            }
        }
        return result;
    }

    private static void addAssistantMessage(
            final List<OpenAiMessage> result, final AssistantMessage message) {
        if (message.getText() != null) {
            result.add(OpenAiMessage.assistant(message.getText()));
            return;
        }
        final Function<ToolCall, OpenAiToolCall> callTranslate =
                toolCall -> OpenAiToolCall.function(toolCall.id(), toolCall.name(), toolCall.arguments());
        val calls = message.getToolCalls().stream().map(callTranslate).toList();
        result.add(OpenAiMessage.assistant(calls));
    }

    private static void addToolMessages(
            final List<OpenAiMessage> result, final ToolResponseMessage message) {
        for (final ToolResponseMessage.ToolResponse response : message.getResponses()) {
            result.add(OpenAiMessage.tool(response.responseData(), response.id()));
        }
    }

    @Nonnull
    private static List<Generation> toGenerations(
            @Nonnull final OpenAiChatCompletionResponse result) {
        return result.getOriginalResponse().getChoices().stream()
                .map(OpenAiChatModel::toGeneration)
                .toList();
    }

    @Nonnull
    private static Generation toGeneration(
            @Nonnull final CreateChatCompletionResponseChoicesInner choice) {
        val metadata =
                ChatGenerationMetadata.builder().finishReason(choice.getFinishReason().getValue());
        metadata.metadata("index", choice.getIndex());
        if (choice.getLogprobs() != null && !choice.getLogprobs().getContent().isEmpty()) {
            metadata.metadata("logprobs", choice.getLogprobs().getContent());
        }
        val message = choice.getMessage();
        val calls = new ArrayList<ToolCall>();
        if (message.getToolCalls() != null) {
            for (final ChatCompletionMessageToolCall c : message.getToolCalls()) {
                val fnc = c.getFunction();
                calls.add(
                        new ToolCall(c.getId(), c.getType().getValue(), fnc.getName(), fnc.getArguments()));
            }
        }

        val assistantMessage = new AssistantMessage(message.getContent(), Map.of(), calls);
        return new Generation(assistantMessage, metadata.build());
    }

    /**
     * Adds options to the request.
     *
     * @param request the request to modify
     * @param options the options to extract
     * @return the modified request with options applied
     */
    @Nonnull
    protected static OpenAiChatCompletionRequest extractOptions(
            @Nonnull OpenAiChatCompletionRequest request, @Nonnull final ChatOptions options) {
        request = request.withStop(options.getStopSequences()).withMaxTokens(options.getMaxTokens());
        if (options.getTemperature() != null) {
            request = request.withTemperature(BigDecimal.valueOf(options.getTemperature()));
        }
        if (options.getTopP() != null) {
            request = request.withTopP(BigDecimal.valueOf(options.getTopP()));
        }
        if (options.getPresencePenalty() != null) {
            request = request.withPresencePenalty(BigDecimal.valueOf(options.getPresencePenalty()));
        }
        if (options.getFrequencyPenalty() != null) {
            request = request.withFrequencyPenalty(BigDecimal.valueOf(options.getFrequencyPenalty()));
        }
        return request;
    }

    private static List<ChatCompletionTool> extractTools(final ToolCallingChatOptions options) {
        val tools = new ArrayList<ChatCompletionTool>();
        for (val toolCallback : options.getToolCallbacks()) {
            val toolDefinition = toolCallback.getToolDefinition();
            try {
                final Map<String, Object> params =
                        new ObjectMapper().readValue(toolDefinition.inputSchema(), new TypeReference<>() {
                        });
                val toolType = ChatCompletionTool.TypeEnum.FUNCTION;
                val toolFunction =
                        new FunctionObject()
                                .name(toolDefinition.name())
                                .description(toolDefinition.description())
                                .parameters(params);
                val tool = new ChatCompletionTool().type(toolType).function(toolFunction);
                tools.add(tool);
            } catch (JsonProcessingException e) {
                log.warn("Failed to add tool to the chat request: {}", e.getMessage());
            }
        }
        return tools;
    }
}
