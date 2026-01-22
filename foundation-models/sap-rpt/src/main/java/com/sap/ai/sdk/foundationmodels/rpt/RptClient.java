package com.sap.ai.sdk.foundationmodels.rpt;

import static com.sap.ai.sdk.core.JacksonConfiguration.getDefaultObjectMapper;

import com.google.common.collect.Iterables;
import com.sap.ai.sdk.core.AiCoreService;
import com.sap.ai.sdk.core.DeploymentResolutionException;
import com.sap.ai.sdk.foundationmodels.rpt.generated.client.DefaultApi;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictRequestPayload;
import com.sap.ai.sdk.foundationmodels.rpt.generated.model.PredictResponsePayload;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.Destination;
import com.sap.cloud.sdk.services.openapi.apiclient.ApiClient;
import javax.annotation.Nonnull;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/** Client for interacting with SAP RPT foundation models. */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RptClient {
  @Nonnull private final DefaultApi api;

  /**
   * Creates a new RptClient for the specified foundation model.
   *
   * @param foundationModel The foundation model to use.
   * @return A new instance of RptClient.
   * @throws DeploymentResolutionException If there is an error resolving the deployment.
   */
  @Nonnull
  public static RptClient forModel(@Nonnull final RptModel foundationModel)
      throws DeploymentResolutionException {
    final var destination = new AiCoreService().getInferenceDestination().forModel(foundationModel);
    return forDestination(destination);
  }

  /**
   * Creates a new RptClient for the specified destination.
   *
   * @param destination The destination to use.
   * @return A new instance of RptClient.
   */
  static RptClient forDestination(@Nonnull final Destination destination) {
    // This is a workaround to override the object mapper to ignore null on serialization
    val httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    httpRequestFactory.setHttpClient(ApacheHttpClient5Accessor.getHttpClient(destination));
    final var rt = new RestTemplate();
    Iterables.filter(rt.getMessageConverters(), MappingJackson2HttpMessageConverter.class)
        .forEach(converter -> converter.setObjectMapper(getDefaultObjectMapper()));
    rt.setRequestFactory(new BufferingClientHttpRequestFactory(httpRequestFactory));

    final var apiClient = new ApiClient(rt).setBasePath(destination.asHttp().getUri().toString());
    return new RptClient(new DefaultApi(apiClient));
  }

  /**
   * Predict targets using SAP RPT model with structured data.
   *
   * <p>
   *
   * <p><b>200</b> - Successful response with predictive insights.
   *
   * <p><b>400</b> - Bad Request - Invalid input.
   *
   * <p><b>422</b> - Unprocessable Content - Invalid input.
   *
   * <p><b>500</b> - Internal Server Error.
   *
   * @param requestBody The value for the parameter tabCompletionPostRequest
   * @return TabCompletionPostResponse
   */
  @Nonnull
  public PredictResponsePayload tabCompletion(@Nonnull final PredictRequestPayload requestBody) {
    return api.predict(requestBody);
  }
}
