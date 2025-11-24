package com.sap.ai.sdk.core.common;

import com.google.common.annotations.Beta;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.slf4j.MDC;

/**
 * Utility for managing MDC (Mapped Diagnostic Context) for logging of AI Core requests.
 *
 * <p>This class is intended for internal use only.
 */
@Slf4j
@UtilityClass
@Beta
public class RequestLogContext {

  private static void setCallId(@Nonnull final String callId) {
    MDC.put(MdcKeys.CALL_ID, callId);
  }

  /**
   * Set the endpoint for the current request context.
   *
   * @param endpoint the endpoint URL
   */
  public static void setEndpoint(@Nonnull final String endpoint) {
    MDC.put(MdcKeys.ENDPOINT, endpoint);
  }

  /**
   * Set the destination for the current request context.
   *
   * @param destination the destination name
   */
  public static void setDestination(@Nonnull final String destination) {
    MDC.put(MdcKeys.DESTINATION, destination);
  }

  /**
   * Set the mode for the current request context.
   *
   * @param mode the request mode
   */
  public static void setMode(@Nonnull final Mode mode) {
    MDC.put(MdcKeys.MODE, mode.getValue());
  }

  /**
   * Set the service for the current request context.
   *
   * @param service the service type
   */
  public static void setService(@Nonnull final Service service) {
    MDC.put(MdcKeys.SERVICE, service.getValue());
  }

  /** Clear all MDC request context information. */
  public static void clear() {
    MDC.remove(MdcKeys.CALL_ID);
    MDC.remove(MdcKeys.ENDPOINT);
    MDC.remove(MdcKeys.DESTINATION);
    MDC.remove(MdcKeys.MODE);
    MDC.remove(MdcKeys.SERVICE);
  }

  /** Log the start of a request with generated request ID. */
  public static void logRequestStart() {
    val callId = UUID.randomUUID().toString().substring(0, 8);
    RequestLogContext.setCallId(callId);

    val message = "[callId={}] Starting {} {} request to {}, destination={}.";
    log.debug(
        message,
        callId,
        MDC.get(MdcKeys.SERVICE),
        MDC.get(MdcKeys.MODE),
        MDC.get(MdcKeys.ENDPOINT),
        MDC.get(MdcKeys.DESTINATION));
  }

  /**
   * Log successful response with duration and size information.
   *
   * @param response the HTTP response
   */
  public static void logResponseSuccess(@Nonnull final ClassicHttpResponse response) {
    if (!log.isDebugEnabled()) {
      return;
    }

    val headerTime = Optional.ofNullable(response.getFirstHeader("x-upstream-service-time"));
    val duration = headerTime.map(h -> h.getValue() + "ms").orElse("unknown");
    val sizeInfo =
        Optional.ofNullable(response.getEntity())
            .map(HttpEntity::getContentLength)
            .filter(length -> length >= 0)
            .map(length -> "%.1fKB".formatted(length / 1024.0))
            .orElse("unknown");
    val message = "[callId={}] {} request completed successfully with duration={}, size={}.";
    log.debug(message, MDC.get(MdcKeys.CALL_ID), MDC.get(MdcKeys.SERVICE), duration, sizeInfo);
  }

  @UtilityClass
  private static class MdcKeys {
    private static final String CALL_ID = "callId";
    private static final String ENDPOINT = "endpoint";
    private static final String DESTINATION = "destination";
    private static final String MODE = "mode";
    private static final String SERVICE = "service";
  }

  /** Request execution modes. */
  @RequiredArgsConstructor
  public enum Mode {
    /** Synchronous request mode */
    SYNCHRONOUS("synchronous"),
    /** Streaming request mode */
    STREAMING("streaming");
    @Getter private final String value;
  }

  /** AI service types. */
  @RequiredArgsConstructor
  public enum Service {
    /** OpenAI service */
    OPENAI("OpenAI"),
    /** Orchestration service */
    ORCHESTRATION("Orchestration");
    @Getter private final String value;
  }
}
