package com.sap.ai.sdk.app.services;

import com.sap.ai.sdk.app.services.MockWeatherService.Request;
import com.sap.ai.sdk.app.services.MockWeatherService.Response;
import java.util.function.Function;

public class MockWeatherService implements Function<Request, Response> {

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
