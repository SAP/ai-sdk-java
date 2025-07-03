package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.app.services.SpringAiAgenticWorkflowService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SpringAiAgenticTest {
  private final SpringAiAgenticWorkflowService service = new SpringAiAgenticWorkflowService();

  @Test
  void testRunAgent() {

    List<Long> times = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      var startTime = System.currentTimeMillis();
      var response =
          service.runAgent(
              "I want to do a one-day trip to Paris. Help me make an itinerary, please");
      var endTime = System.currentTimeMillis();
      times.add(endTime - startTime);
      System.out.printf("-----time: %s --------\n", endTime - startTime);
    }
    double average = times.stream().mapToLong(Long::longValue).average().orElse(0);
    var standard_deviation =
        Math.sqrt(
            times.stream()
                .mapToLong(Long::longValue)
                .mapToDouble(time -> Math.pow(time - average, 2))
                .average()
                .orElse(0));
    System.out.printf(
        "Average: %s Std Deviation: %s Max: %s Min: %s",
        average,
        standard_deviation,
        times.stream().mapToLong(Long::longValue).max().orElse(0),
        times.stream().mapToLong(Long::longValue).min().orElse(0));
  }
}
