package com.sap.ai.sdk.foundationmodels.openai;

public sealed interface OpenAiToolCallItem extends OpenAiContentItem
    permits OpenAiFunctionCallItem {}
