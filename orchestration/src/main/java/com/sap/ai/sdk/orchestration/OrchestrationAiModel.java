package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import java.util.Map;
import javax.annotation.Nonnull;

/** Large language models available in Orchestration. */
// https://help.sap.com/docs/sap-ai-core/sap-ai-core-service-guide/models-and-scenarios-in-generative-ai-hub
@SuppressWarnings("unused") // tested in OrchestrationTest.orchestrationModelAvailability()
public class OrchestrationAiModel extends LLMModuleConfig {

  /** IBM Granite 13B chat completions model */
  public static final OrchestrationAiModel IBM_GRANITE_13B_CHAT =
      new OrchestrationAiModel("ibm--granite-13b-chat");

  /** MistralAI Mistral Large Instruct model */
  public static final OrchestrationAiModel MISTRAL_LARGE_INSTRUCT =
      new OrchestrationAiModel("mistralai--mistral-large-instruct");

  /** MistralAI Mixtral 8x7B Instruct v01 model */
  public static final OrchestrationAiModel MIXTRAL_8X7B_INSTRUCT_V01 =
      new OrchestrationAiModel("mistralai--mixtral-8x7b-instruct-v01");

  /** Meta Llama3 70B Instruct model */
  public static final OrchestrationAiModel LLAMA3_70B_INSTRUCT =
      new OrchestrationAiModel("meta--llama3-70b-instruct");

  /** Meta Llama3.1 70B Instruct model */
  public static final OrchestrationAiModel LLAMA3_1_70B_INSTRUCT =
      new OrchestrationAiModel("meta--llama3.1-70b-instruct");

  /** Anthropic Claude 3 Sonnet model */
  public static final OrchestrationAiModel CLAUDE_3_SONNET =
      new OrchestrationAiModel("anthropic--claude-3-sonnet");

  /** Anthropic Claude 3 Haiku model */
  public static final OrchestrationAiModel CLAUDE_3_HAIKU =
      new OrchestrationAiModel("anthropic--claude-3-haiku");

  /** Anthropic Claude 3 Opus model */
  public static final OrchestrationAiModel CLAUDE_3_OPUS =
      new OrchestrationAiModel("anthropic--claude-3-opus");

  /** Anthropic Claude 3.5 Sonnet model */
  public static final OrchestrationAiModel CLAUDE_3_5_SONNET =
      new OrchestrationAiModel("anthropic--claude-3.5-sonnet");

  /** Amazon Titan Embed Text model */
  public static final OrchestrationAiModel TITAN_EMBED_TEXT =
      new OrchestrationAiModel("amazon--titan-embed-text");

  /** Amazon Titan Text Lite model */
  public static final OrchestrationAiModel TITAN_TEXT_LITE =
      new OrchestrationAiModel("amazon--titan-text-lite");

  /** Amazon Titan Text Express model */
  public static final OrchestrationAiModel TITAN_TEXT_EXPRESS =
      new OrchestrationAiModel("amazon--titan-text-express");

  /** Azure OpenAI GPT-3.5 Turbo chat completions model */
  public static final OrchestrationAiModel GPT_35_TURBO = new OrchestrationAiModel("gpt-35-turbo");

  /** Azure OpenAI GPT-3.5 Turbo chat completions model */
  public static final OrchestrationAiModel GPT_35_TURBO_16K =
      new OrchestrationAiModel("gpt-35-turbo-16k");

  /** Azure OpenAI GPT-4 chat completions model */
  public static final OrchestrationAiModel GPT_4 = new OrchestrationAiModel("gpt-4");

  /** Azure OpenAI GPT-4-32k chat completions model */
  public static final OrchestrationAiModel GPT_4_32K = new OrchestrationAiModel("gpt-4-32k");

  /** Azure OpenAI Text Embedding Ada 002 model */
  public static final OrchestrationAiModel TEXT_EMBEDDING_ADA_002 =
      new OrchestrationAiModel("text-embedding-ada-002");

  /** Azure OpenAI Text Embedding 3 Small model */
  public static final OrchestrationAiModel TEXT_EMBEDDING_3_SMALL =
      new OrchestrationAiModel("text-embedding-3-small");

  /** Azure OpenAI Text Embedding 3 Large model */
  public static final OrchestrationAiModel TEXT_EMBEDDING_3_LARGE =
      new OrchestrationAiModel("text-embedding-3-large");

  /** Azure OpenAI GPT-4o chat completions model */
  public static final OrchestrationAiModel GPT_4O = new OrchestrationAiModel("gpt-4o");

  /** Azure OpenAI GPT-4o-mini chat completions model */
  public static final OrchestrationAiModel GPT_4O_MINI = new OrchestrationAiModel("gpt-4o-mini");

  /** Google Cloud Platform Text Bison model */
  public static final OrchestrationAiModel TEXT_BISON = new OrchestrationAiModel("text-bison");

  /** Google Cloud Platform Chat Bison model */
  public static final OrchestrationAiModel CHAT_BISON = new OrchestrationAiModel("chat-bison");

  /** Google Cloud Platform Text Embedding Gecko model */
  public static final OrchestrationAiModel TEXT_EMBEDDING_GECKO =
      new OrchestrationAiModel("textembedding-gecko");

  /** Google Cloud Platform Text Embedding Gecko Multilingual model */
  public static final OrchestrationAiModel TEXT_EMBEDDING_GECKO_MULTILINGUAL =
      new OrchestrationAiModel("textembedding-gecko-multilingual");

  /** Google Cloud Platform Gemini 1.0 Pro model */
  public static final OrchestrationAiModel GEMINI_1_0_PRO =
      new OrchestrationAiModel("gemini-1.0-pro");

  /** Google Cloud Platform Gemini 1.5 Pro model */
  public static final OrchestrationAiModel GEMINI_1_5_PRO =
      new OrchestrationAiModel("gemini-1.5-pro");

  /** Google Cloud Platform Gemini 1.5 Flash model */
  public static final OrchestrationAiModel GEMINI_1_5_FLASH =
      new OrchestrationAiModel("gemini-1.5-flash");

  OrchestrationAiModel(@Nonnull final String modelName) {
    setModelName(modelName);
    setModelParams(Map.of());
  }
}
