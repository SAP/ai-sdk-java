package com.sap.ai.sdk.orchestration.spring;

import javax.annotation.Nonnull;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class WeatherMethod {

  /** Unit of temperature */
  public enum Unit {
    /** Celsius */
    @SuppressWarnings("unused")
    C,
    /** Fahrenheit */
    @SuppressWarnings("unused")
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

  @Nonnull
  @SuppressWarnings("unused")
  @Tool(description = "Get the weather in location")
  Response getCurrentWeather(@ToolParam @Nonnull Request request) {
    return new Response(30, request.unit);
  }
}
