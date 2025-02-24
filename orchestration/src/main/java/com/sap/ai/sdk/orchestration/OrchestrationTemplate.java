package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.ChatCompletionTool;
import com.sap.ai.sdk.orchestration.model.ChatMessage;
import com.sap.ai.sdk.orchestration.model.SingleChatMessage;
import com.sap.ai.sdk.orchestration.model.Template;
import com.sap.ai.sdk.orchestration.model.TemplateResponseFormat;
import com.sap.ai.sdk.orchestration.model.TemplatingModuleConfig;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Value
@With
//@AllArgsConstructor(access = AccessLevel.NONE)
public class OrchestrationTemplate extends TemplateConfig{
  List<ChatMessage> template = new ArrayList<>();
  Map<String, String> defaults = new HashMap<>();
  TemplateResponseFormat responseFormat = null;
  List<ChatCompletionTool> tools = new ArrayList<>();
  Map<String, Object> cloudSdkCustomFields = new LinkedHashMap<>();

  @Override
  protected TemplatingModuleConfig toLowLevel() {
    return Template.create().template(template).defaults(defaults).responseFormat(responseFormat).tools(tools);
  }
}
