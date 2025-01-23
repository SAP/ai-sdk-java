package com.sap.ai.sdk.foundationmodels.openai;

import com.sap.ai.sdk.core.common.ClientException;
import lombok.experimental.StandardException;

/** Generic exception for errors occurring when using OpenAI foundation models. */
@StandardException
public class OpenAiClientException extends ClientException {}
