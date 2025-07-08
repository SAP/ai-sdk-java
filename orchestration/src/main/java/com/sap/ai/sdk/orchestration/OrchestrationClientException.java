package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.core.common.ClientException;
import lombok.Getter;
import lombok.experimental.StandardException;

/** Exception thrown by the {@link OrchestrationClient} in case of an error. */
@StandardException
@Getter
public class OrchestrationClientException extends ClientException {}
