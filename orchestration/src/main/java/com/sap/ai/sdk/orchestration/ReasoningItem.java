package com.sap.ai.sdk.orchestration;

import javax.annotation.Nonnull;

/**
 * A reasoning (thinking) block produced by a reasoning-capable model.
 *
 * @param content the reasoning text produced by the model.
 * @param signature an opaque replay signature; may be empty.
 * @see <a href="https://help.sap.com/docs/sap-ai-core/generative-ai/reasoning">SAP AI Core:
 *     Orchestration - Reasoning</a>
 */
public record ReasoningItem(@Nonnull String content, @Nonnull String signature) {}
