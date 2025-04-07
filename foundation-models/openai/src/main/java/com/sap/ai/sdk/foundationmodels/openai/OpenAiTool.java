package com.sap.ai.sdk.foundationmodels.openai;

/**
 * Represents a tool that can be integrated into an OpenAI Chat Completion request.
 *
 * @since 1.7.0
 */
public sealed interface OpenAiTool permits OpenAiFunctionTool {}
