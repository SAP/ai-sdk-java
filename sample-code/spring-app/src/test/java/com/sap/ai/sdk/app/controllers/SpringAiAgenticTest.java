package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.app.services.SpringAiAgenticWorkflowService;
import com.sap.ai.sdk.core.common.ClientResponseHandler;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class SpringAiAgenticTest {
  private final SpringAiAgenticWorkflowService service = new SpringAiAgenticWorkflowService();

  @Test
  void testRunAgent() {

    List<Long> times = new ArrayList<>();
    List<Long> realTimes = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      var startTime = System.currentTimeMillis();
      var response =
          service.runAgent(
              "I want to do a one-day trip to Paris. Help me make an itinerary, please");
      var endTime = System.currentTimeMillis();
      times.add(endTime - startTime);

      realTimes.add(ClientResponseHandler.time);
      ClientResponseHandler.time = 0;
    }

    System.out.println("Java time");
    for (Long aLong : times) {
      System.out.printf("%d\n", aLong);
    }
    final double average = times.stream().mapToLong(Long::longValue).average().orElse(0);
    double standard_deviation =
        Math.sqrt(
            times.stream()
                .mapToLong(Long::longValue)
                .mapToDouble(time -> Math.pow(time - average, 2))
                .average()
                .orElse(0));
    System.out.printf(
        "Average: %s Std Deviation: %s Max: %s Min: %s%n",
        average,
        standard_deviation,
        times.stream().mapToLong(Long::longValue).max().orElse(0),
        times.stream().mapToLong(Long::longValue).min().orElse(0));


    System.out.println("x-upstream-service-time");
    for (Long aLong : realTimes) {
      System.out.printf("%d\n", aLong);
    }
    final double realAverage = realTimes.stream().mapToLong(Long::longValue).average().orElse(0);
    standard_deviation =
        Math.sqrt(
            realTimes.stream()
                .mapToLong(Long::longValue)
                .mapToDouble(time -> Math.pow(time - realAverage, 2))
                .average()
                .orElse(0));
    System.out.printf(
        "Average: %s Std Deviation: %s Max: %s Min: %s",
        realAverage,
        standard_deviation,
        realTimes.stream().mapToLong(Long::longValue).max().orElse(0),
        realTimes.stream().mapToLong(Long::longValue).min().orElse(0));
  }
}
