package com.sap.ai.sdk.foundationmodels.anthropic;

import com.sap.ai.sdk.core.common.ClientException;
import com.sap.ai.sdk.core.common.ClientExceptionFactory;
import lombok.experimental.StandardException;

/**
 * Exception thrown when an Anthropic API call fails.
 *
 * @since 1.26.0
 */
@StandardException
public class AnthropicClientException extends ClientException {

  static final ClientExceptionFactory<AnthropicClientException, AnthropicError> FACTORY =
      (message, error, cause) -> new AnthropicClientException(message, cause).setClientError(error);
}
