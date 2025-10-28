package com.sap.ai.sdk.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.cloud.environment.servicebinding.api.DefaultServiceBindingBuilder;
import com.sap.cloud.environment.servicebinding.api.ServiceBinding;
import com.sap.cloud.environment.servicebinding.api.ServiceBindingAccessor;
import com.sap.cloud.environment.servicebinding.api.ServiceIdentifier;
import com.sap.cloud.environment.servicebinding.api.exception.ServiceBindingAccessException;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvBuilder;
import io.vavr.Lazy;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

/**
 * Loads an AI Core service key from the "AICORE_SERVICE_KEY" environment variable with support for
 * .env files. Will ignore missing or malformed dotenv files.
 */
@Slf4j
@AllArgsConstructor
class AiCoreServiceKeyAccessor implements ServiceBindingAccessor {
  static final String ENV_VAR_KEY = "AICORE_SERVICE_KEY";

  private final Lazy<Dotenv> dotenv;

  AiCoreServiceKeyAccessor() {
    this(Dotenv.configure().ignoreIfMissing().ignoreIfMalformed());
  }

  AiCoreServiceKeyAccessor(@Nonnull final DotenvBuilder dotenvBuilder) {
    dotenv = Lazy.of(dotenvBuilder::load);
  }

  @Nonnull
  @Override
  public List<ServiceBinding> getServiceBindings() throws ServiceBindingAccessException {
    final String serviceKey;
    try {
      serviceKey = dotenv.get().get(ENV_VAR_KEY);
    } catch (Exception e) {
      throw new ServiceBindingAccessException("Failed to load service key from environment", e);
    }
    if (serviceKey == null) {
      log.debug("No service key found in environment variable {}", ENV_VAR_KEY);
      return List.of();
    }
    log.info(
        """
        Found a service key in environment variable {}.
        Using a service key is recommended for local testing only.
        Bind the AI Core service to the application for productive usage.
        """,
        ENV_VAR_KEY);

    val binding = createServiceBinding(serviceKey);
    return List.of(binding);
  }

  static ServiceBinding createServiceBinding(@Nonnull final String serviceKey)
      throws ServiceBindingAccessException {
    final HashMap<String, Object> credentials;
    try {
      credentials = new ObjectMapper().readValue(serviceKey, new TypeReference<>() {});
    } catch (JsonProcessingException e) {
      throw new ServiceBindingAccessException(
          new AiCoreCredentialsInvalidException(
              "Error in parsing service key from the " + ENV_VAR_KEY + " environment variable.",
              e));
    }
    if (credentials.get("clientid") == null) {
      // explicitly check for the client ID to improve the error message
      // otherwise we get a rather generic DestinationNotFoundException error that none of the
      // loaders matched the binding
      throw new ServiceBindingAccessException(
          new AiCoreCredentialsInvalidException("Missing clientid in service key"));
    }

    if (credentials.get("clientsecret") != null && credentials.get("credential-type") == null) {
      // add missing "credential-type: binding-secret"
      credentials.put("credential-type", "binding-secret");
    }

    return new DefaultServiceBindingBuilder()
        .withServiceIdentifier(ServiceIdentifier.AI_CORE)
        .withCredentials(credentials)
        .build();
  }
}
