package com.sap.ai.sdk.grounding;

import com.sap.ai.sdk.core.common.ClientException;
import lombok.experimental.StandardException;

/** Generic exception for errors occurring when using OpenAI foundation models. */
@StandardException
public class GroundingClientException extends ClientException {}
