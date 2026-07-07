package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.openai.models.responses.Response;
import com.sap.cloud.sdk.cloudplatform.connectivity.Header;
import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Flow;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class RealtimeClientFactory {
    private static final String AI_RESOURCE_GROUP_HEADER_NAME = "AI-Resource-Group";
    private static final String AI_RESOURCE_GROUP_HEADER_VALUE = "default";
    private static final String REALTIME_API_PATH_SUFFIX = "/v2/inference/deployments/%s/v1/realtime";
    private static final int PATH_BUFFER_SIZE = 400; // existing URLs are ~120 symbols long, 400 has reasonable margin


    private static final String REALTIME_API_PATH =
            "wss://realtime.ai.intprod-eu12.eu-central-1.aws.ml.hana.ondemand.com/v2/inference/deployments/ddb5a959fdb51ffe/v1/realtime";

    private final Supplier<HttpDestination> destinationSupplier;
    private final Supplier<String> deploymentIdSupplier;

    /**
     * Voices input text into output audio
     *
     * @param input text to voice
     * @return byte chunks output stream in PCM16 format, 16-bit, mono, 24000 Hz, little-endian
     */
    public Stream<byte[]> textToSpeech(@Nonnull final Consumer<byte[]> audioOutputConsumer) {
        var destination = destinationSupplier.get();

        var extraHeaders = destination.getHeaders();
        var headers = new HashMap<String, String>(extraHeaders.size() + 1);
        headers.put(AI_RESOURCE_GROUP_HEADER_NAME, AI_RESOURCE_GROUP_HEADER_VALUE);
        for (Header header : extraHeaders) {
            headers.put(header.getName(), header.getValue());
        }

        var deploymentId = deploymentIdSupplier.get();
        var sb = new StringBuilder(PATH_BUFFER_SIZE);
        sb.append("wss://");
        sb.append(destination.getUri().getHost());
        // set port explicitly if defined in the uri
        if (destination.getUri().getPort() != -1) {
            sb.append(":").append(destination.getUri().getPort());
        }
        sb.append(String.format(REALTIME_API_PATH_SUFFIX, deploymentId));
        var endpoint = sb.toString();


        final List<byte[]> result = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient(); // TODO: fix local setup and use autocloseable
            WebSocket ws =
                    client
                            .newWebSocketBuilder()
                            .header(
                                    "Authorization",
                                    "Bearer ....")
                            .header("AI-Resource-Group", "default")
                            .buildAsync(
                                    URI.create(endpoint),
                                    new WebSocket.Listener() {

                                        @Override
                                        public void onOpen(WebSocket webSocket) {
                                            log.warn("Websocket connection opened");
                                            String sessionUpdateJson =
                                                    """
                                                      {
                                                        "type": "session.update",
                                                        "session": {
                                                          "type": "realtime",
                                                          "output_modalities": ["audio"],
                                                          "instructions": "you are a speaker and your role is to read (produce audio) of the user input speech. voice user text input",
                                                          "audio": {
                                                            "input": { "format": { "type": "audio/pcm", "rate": 24000 } },
                                                            "output": { "format": { "type": "audio/pcm", "rate": 24000 }, "voice": "alloy" }
                                                          }
                                                        }
                                                      }
                                                      """;

                                            // String sessionUpdateJson = "{\"type\": \"session.update\", \"session\":
                                            // {\"modalities\": [\"audio\", \"text\"]}}";
                                            webSocket.sendText(sessionUpdateJson, true);
                                            webSocket.request(1);

                                            String textMessage =
                                                    """
                          {
                            "type": "conversation.item.create",
                            "item": {
                              "type": "message",
                              "role": "system",
                              "content": [
                                {
                                  "type": "input_text",
                                  "text": "you are a speaker and your role is to read (produce audio) of the user input speech. voice user text input"
                                }
                              ]
                            }
                          }
                          """;
                                            //.replaceFirst("%VALUE%", input); // TODO: sanitize or/and escape input
                                            log.warn("Sending message to websocket: {}", textMessage);
                                            webSocket.sendText(textMessage, true);
                                            webSocket.request(1);

                                            String voiceMessage =
                                                    """
                          {
                            "type": "conversation.item.create",
                            "item": {
                              "type": "message",
                              "role": "user",
                              "content": [
                                {
                                  "type": "input_text",
                                  "text": "Eat more of these fresh french baguettes"
                                }
                              ]
                            }
                          }
                          """;

                                            webSocket.sendText(voiceMessage, true);
                                            webSocket.request(1);




                                            String askForResponse = "{ \"type\": \"response.create\" }";
                                            webSocket.sendText(askForResponse, true);

                                            webSocket.request(80);
                                        }

                                        @Override
                                        public CompletionStage<?> onText(
                                                WebSocket webSocket, CharSequence data, boolean last) {
                                            try {
                                                Thread.sleep(500);
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                            log.warn("text received: {}", data);
                                            final JsonNode event;
                                            try {
                                                event = JACKSON.readTree(data.toString());
                                            } catch (JsonProcessingException e) {
                                                throw new OrchestrationClientException(
                                                        "Error parsing JSON response from speech API", e);
                                            }
                                            Response r = null;

                                            String type = event.get("type").asText();
                                            if ("response.output_audio.delta".equals(type)) {
                                                String base64Audio = event.get("delta").asText();
                                                byte[] audio = Base64.getDecoder().decode(base64Audio);
                                                result.add(audio);
                                            } else if ("response.audio_transcript.delta".equals(type)) {
                                                log.warn("RECEIVED TRANSCRIPT: {}", event.get("delta").asText());
                                            } else if ("response.output_audio.done".equals(type)) {
                                                // TODO: we should not wait last ack, we should return stream immediately,
                                                // this requires
                                                // TODO: thread management, factory and thread safety of all structs
                                                webSocket.sendClose(1000, "done");
                                                return null;
                                            } else {
                                                log.warn("Unhandled event type: {}", data);
                                            }
                                            return CompletableFuture.completedStage(null);
                                        }
                                    })
                            .join();
            Thread.sleep(25000);
            return result.stream();
        } catch (Exception e) {
            //log.error(e.getMessage(), e);
            throw new OrchestrationClientException("Failed to call realtime API", e);
        }
    }

}
