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
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

class ApiClientBuilder {

  @RequiredArgsConstructor
  enum ApiClientConstructor {
    SERIALIZE_WITH_NULL_VALUES(ApiClient::new),
    SERIALIZE_WITHOUT_NULL_VALUES(ApiClientConstructor::withoutNull);

    @SuppressWarnings("UnstableApiUsage")
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

    final Function<Destination, ApiClient> finalizer;
  }
}
