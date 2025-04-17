package com.sap.ai.sdk.app.services;

import javax.annotation.Nonnull;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

class WeatherMethod {

  /** Unit of temperature */
  enum Unit {
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
  record Request(String location, Unit unit) {}

  /**
   * Response for the weather
   *
   * @param temp the temperature
   * @param unit the unit of temperature
   */
  record Response(double temp, Unit unit) {}

  @Nonnull
  @SuppressWarnings("unused")
  @Tool(description = "Get the weather in location")
  static Response getCurrentWeather(@ToolParam @Nonnull final Request request) {
    final int temperature = request.location.hashCode() % 30;
    return new Response(temperature, request.unit);
  }
}
