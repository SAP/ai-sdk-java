package com.sap.ai.sdk.app.controllers;

import com.auth0.jwt.JWT;
import com.sap.cloud.environment.servicebinding.SapVcapServicesServiceBindingAccessor;
import com.sap.cloud.environment.servicebinding.api.DefaultServiceBindingAccessor;
import com.sap.cloud.environment.servicebinding.api.ServiceIdentifier;
import com.sap.cloud.sdk.cloudplatform.connectivity.ApacheHttpClient5Accessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.BtpServiceOptions.AuthenticationServiceOptions;
import com.sap.cloud.sdk.cloudplatform.connectivity.BtpServiceOptions.IasOptions;
import com.sap.cloud.sdk.cloudplatform.connectivity.OnBehalfOf;
import com.sap.cloud.sdk.cloudplatform.connectivity.ServiceBindingDestinationLoader;
import com.sap.cloud.sdk.cloudplatform.connectivity.ServiceBindingDestinationOptions;
import com.sap.cloud.sdk.cloudplatform.security.AuthToken;
import com.sap.cloud.sdk.cloudplatform.security.AuthTokenAccessor;
import io.vavr.control.Try;
import java.util.Collections;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.BasicHttpClientResponseHandler;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * How to run this test:
 *
 * <p><b>1. Get the VCAP_SERVICES</b>: Navigate to <a *
 * href="https://canary.cockpit.btp.int.sap/cockpit/#/globalaccount/92129a35-5f7b-4e98-ba96-ca830111f86c/subaccount/94bbb59e-56ff-4cf3-9787-f6b28d5e565b/org/f29b2548-6ca5-410b-8a1e-0d90641a1f40/space/397b46f3-1311-4b1d-99a4-bf19efecfd7b/app/d1a35dbe-cad3-4795-8593-6a21b505cacd/environmentvariables">BTP
 * > agent-approuter > environment variables</a> and copy the <code>VCAP_SERVICES</code> part
 * starting with <code>{"identity"</code>.
 *
 * <p><b>2. Get the JSON_WEB_TOKEN</b>: Copy your password while logging into <a
 * href="https://ma.accounts400.ondemand.com">Cloud Identity</a>. Make a Bruno request to <code>
 * https://ma.accounts400.ondemand.com/oauth2/token</code> with the following URL encoded body:
 *
 * <pre>
 *   grant_type=client_credentials
 *   &client_id=&ltapprouter-client-id>
 *   &client_secret=&ltapprouter-client-secret>
 *   &username=&ltyour-email>
 *   &password=&ltCloud-Indentity-password>
 * </pre>
 */
class AgentToolIntegrationLayerTest {

  private static final String VCAP_SERVICES =
"""
""";

  private static final String JSON_WEB_TOKEN =
"""
""";

  String payload =
      "{\"message\": {\"role\": \"user\",\"parts\": [{  \"kind\": \"data\",  \"data\": {  \"userQuery\": \"Plan a travel itinerary for me for Berlin\"  }}],\"kind\": \"message\"}}";

  String payloadBig =
"""
{
    "message": {
        "role": "user",
        "parts": [
        {
            "kind": "data",
            "data": {
                "userQuery": "Plan a travel itinerary for me for Berlin"
            }
        }
        ],
        "kind": "message"
    }
}
""";

  @BeforeEach
  void prepareInboundAccessToken() {
    DefaultServiceBindingAccessor.setInstance(
        new SapVcapServicesServiceBindingAccessor(
            Collections.singletonMap("VCAP_SERVICES", VCAP_SERVICES)::get));
    AuthTokenAccessor.setAuthTokenFacade(
        () -> Try.success(new AuthToken(JWT.decode(JSON_WEB_TOKEN))));
  }

  @Test
  @SneakyThrows
  void testAgentToolIntegrationLayer() {
    var options =
        ServiceBindingDestinationOptions.forService(ServiceIdentifier.IDENTITY_AUTHENTICATION)
            .withOption(
                AuthenticationServiceOptions.withTargetUri(
                    "https://gen-ai-hub-sdk-8pengs8d.eu12.sapdas.cloud.sap"))
            .withOption(IasOptions.withApplicationName("Agent2Joule"))
            .onBehalfOf(OnBehalfOf.NAMED_USER_CURRENT_TENANT)
            .build();

    var destination = ServiceBindingDestinationLoader.defaultLoaderChain().getDestination(options);

    String atilUrl =
        "https://gen-ai-hub-sdk-8pengs8d.eu12.sapdas.cloud.sap/api/content/v1/capabilities/com.sap.ai.sdk/my_assistant_capability/scenarios/plan_travel/v1/message:send?assistant=my_assistant_i563080";
    var httpPost = new HttpPost(atilUrl);
    httpPost.setEntity(new StringEntity(payload, ContentType.APPLICATION_JSON));
    httpPost.setHeader(
        "x-callback-target", "https://echo-server.cfapps.eu12-001.hana.ondemand.com");
    httpPost.setHeader("x-global-user-id", "01361f5a-9ca7-4593-9c54-2425a127edff");
    httpPost.setHeader("Accept-Language", "en");

    var response =
        ApacheHttpClient5Accessor.getHttpClient(destination)
            .execute(httpPost, new BasicHttpClientResponseHandler());
    System.out.println(response);
  }
}
