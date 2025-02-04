package com.sap.ai.sdk.orchestration.spring;

import java.util.function.Function;

public class MockWeatherService
    implements Function<MockWeatherService.Request, MockWeatherService.Response> {

  public enum Unit {
    C,
    F
  }

  public record Request(String location, Unit unit) {}

  public record Response(double temp, Unit unit) {}

  public Response apply(Request request) {
    return new Response(30.0, Unit.C);
  }
}
