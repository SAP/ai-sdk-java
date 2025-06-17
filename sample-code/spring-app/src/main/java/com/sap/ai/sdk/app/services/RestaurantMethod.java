package com.sap.ai.sdk.app.services;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nonnull;
import lombok.val;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

/** Mock tool for agentic workflow */
class RestaurantMethod {

  /**
   * Request for list of restaurants
   *
   * @param location the city
   */
  record Request(String location) {}

  /**
   * Response for restaurant recommendations
   *
   * @param restaurants the list of restaurants
   */
  record Response(List<String> restaurants) {}

  @Nonnull
  @SuppressWarnings("unused")
  @Tool(description = "Get recommended restaurants for a location")
  static RestaurantMethod.Response getRestaurants(
      @ToolParam @Nonnull final RestaurantMethod.Request request) {
    val recommendations =
        Map.of(
            "paris",
            List.of("Le Comptoir du Relais", "L'As du Fallafel", "Breizh Café"),
            "reykjavik",
            List.of("Dill Restaurant", "Fish Market", "Grillmarkaðurinn"));
    return new RestaurantMethod.Response(
        recommendations.getOrDefault(
            request.location.toLowerCase(Locale.ROOT),
            List.of("No recommendations for this city.")));
  }
}
