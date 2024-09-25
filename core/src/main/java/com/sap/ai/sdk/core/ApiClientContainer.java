package com.sap.ai.sdk.core;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Iterables;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import java.util.function.Function;
import javax.annotation.Nonnull;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@FunctionalInterface
public interface ApiClientContainer {
  @Nonnull
  Destination getDestination();

  @Nonnull
  default ApiClient getClient() {
    return getClient(ClientOptions.SERIALIZE_WITHOUT_NULL_VALUES);
  }

  @Nonnull
  default ApiClient getClient(@Nonnull final ClientOptions options) {
    final Destination destination = getDestination();
    return options.getInitializer().apply(destination);
  }

  interface ClientOptions {
    @Nonnull
    Function<Destination, ApiClient> getInitializer();

    ClientOptions SERIALIZE_WITH_NULL_VALUES = () -> ApiClient::new;

    ClientOptions SERIALIZE_WITHOUT_NULL_VALUES = () -> ClientOptions::withoutNull;

    @SuppressWarnings("UnstableApiUsage")
    @Nonnull
    private static ApiClient withoutNull(@Nonnull final Destination destination) {
      final var objectMapper =
          new Jackson2ObjectMapperBuilder()
              .modules(new JavaTimeModule())
              .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
              .visibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE)
              .serializationInclusion(
                  JsonInclude.Include.NON_NULL) // THIS STOPS `null` serialization
              .build();

      final var httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
      httpRequestFactory.setHttpClient(ApacheHttpClient5Accessor.getHttpClient(destination));

      final var rt = new RestTemplate();
      Iterables.filter(rt.getMessageConverters(), MappingJackson2HttpMessageConverter.class)
          .forEach(converter -> converter.setObjectMapper(objectMapper));
      rt.setRequestFactory(new BufferingClientHttpRequestFactory(httpRequestFactory));

      return new ApiClient(rt).setBasePath(destination.asHttp().getUri().toString());
    }
  }
}
