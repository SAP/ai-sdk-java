package com.sap.ai.sdk.core.common;

import static com.sap.ai.sdk.core.common.RequestLogContext.Mode.STREAMING;
import static com.sap.ai.sdk.core.common.RequestLogContext.Service.OPENAI;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

class RequestLogContextTest {

  @Test
  void testRequestLogContextLifecycle() {
    // Setup customer MDC entries (to test clear() safety)
    MDC.put("consumer-key", "consumer-value");

    RequestLogContext.setService(OPENAI);
    RequestLogContext.setMode(STREAMING);
    RequestLogContext.setEndpoint("/api/endpoint");
    RequestLogContext.setDestination("http://localhost:8000");

    assertThat(MDC.get("service")).isEqualTo("openai");
    assertThat(MDC.get("mode")).isEqualTo("streaming");
    assertThat(MDC.get("endpoint")).isEqualTo("/api/endpoint");
    assertThat(MDC.get("destination")).isEqualTo("http://localhost:8000");

    RequestLogContext.logRequestStart();
    assertThat(MDC.get("reqId")).isNotNull().hasSize(8);

    RequestLogContext.clear();
    assertThat(MDC.get("service")).isNull();
    assertThat(MDC.get("mode")).isNull();
    assertThat(MDC.get("endpoint")).isNull();
    assertThat(MDC.get("destination")).isNull();
    assertThat(MDC.get("reqId")).isNull();
    assertThat(MDC.get("consumer-key")).isEqualTo("consumer-value");
  }
}
