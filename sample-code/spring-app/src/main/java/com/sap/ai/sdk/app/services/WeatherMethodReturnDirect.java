package com.sap.ai.sdk.app.services;

import javax.annotation.Nonnull;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * Tool class demonstrating {@code returnDirect=true}: the result is returned straight to the caller
 * without being sent back to the LLM for a second completion.
 */
class WeatherMethodReturnDirect {

  enum Unit {
    @SuppressWarnings("unused")
    C,
    @SuppressWarnings("unused")
    F
  }

  /**
   * Request for the weather.
   *
   * @param location the city.
   * @param unit the unit of temperature.
   */
  record Request(String location, WeatherMethod.Unit unit) {}

  /**
   * Response for the weather.
   *
   * @param temp the temperature.
   * @param unit the unit of temperature.
   */
  record Response(double temp, Unit unit) {}

  @Nonnull
  @SuppressWarnings("unused")
  @Tool(description = "Get the weather in location", returnDirect = true)
  Response getCurrentWeather(
      @ToolParam(description = "the city") @Nonnull final String location,
      @ToolParam(description = "the unit of temperature") @Nonnull final Unit unit) {
    final int temperature = location.hashCode() % 30;
    return new Response(temperature, unit);
  }
}
