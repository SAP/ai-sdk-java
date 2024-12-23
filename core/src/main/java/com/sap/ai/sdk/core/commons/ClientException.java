package com.sap.ai.sdk.core.commons;

import com.google.common.annotations.Beta;
import lombok.experimental.StandardException;

/**
 * Generic exception for errors occurring when using AI SDK clients.
 *
 * @since 1.1.0
 */
@Beta
@StandardException
public class ClientException extends RuntimeException {}
