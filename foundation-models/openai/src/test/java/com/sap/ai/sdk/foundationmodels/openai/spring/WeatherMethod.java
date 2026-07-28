package com.sap.ai.sdk.foundationmodels.openai.spring;

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
   * Response for the weather
   *
   * @param temp the temperature
   * @param unit the unit of temperature
   */
  public record Response(double temp, Unit unit) {}

  @Nonnull
  @SuppressWarnings("unused")
  @Tool(description = "Get the weather in location")
  Response getCurrentWeather(
      @ToolParam(description = "the city") @Nonnull final String location,
      @ToolParam(description = "the unit of temperature") @Nonnull final Unit unit) {
    return new Response(30, unit);
  }
}
