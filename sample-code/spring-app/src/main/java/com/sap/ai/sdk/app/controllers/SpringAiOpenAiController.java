package com.sap.ai.sdk.app.controllers;

import com.sap.ai.sdk.app.services.SpringAiOpenAiService;
import javax.annotation.Nullable;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/spring-ai-openai")
class SpringAiOpenAiController {
  @Autowired private SpringAiOpenAiService service;

  @GetMapping("/embed/strings")
  Object embed(@Nullable @RequestParam(value = "format", required = false) final String format) {
    val response = service.embedStrings();

    if ("json".equals(format)) {
      return response;
    }
    return response.getResult().getOutput();
  }
}
