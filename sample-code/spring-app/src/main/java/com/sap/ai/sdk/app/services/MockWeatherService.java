package com.sap.ai.sdk.app.services;

import com.sap.ai.sdk.app.services.MockWeatherService.Request;
import com.sap.ai.sdk.app.services.MockWeatherService.Response;
import java.util.function.Function;
import javax.annotation.Nonnull;

/** Function for tool calls in Spring AI */
public class MockWeatherService implements Function<Request, Response> {

  /** Unit of temperature */
  public enum Unit {
    /** Celsius */
    C,
    /** Fahrenheit */
    F
  }

  /**
   * Request for the weather
   *
   * @param location the city
   * @param unit the unit of temperature
   */
  public record Request(String location, Unit unit) {}

  /**
   * Response for the weather
   *
   * @param temp the temperature
   * @param unit the unit of temperature
   */
  public record Response(double temp, Unit unit) {}

  /**
   * Apply the function
   *
   * @param request the request
   * @return the response
   */
  @Nonnull
  public Response apply(@Nonnull Request request) {
    return new Response(30.0, Unit.C);
  }
}
