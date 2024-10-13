package com.sap.ai.sdk.orchestration.spring;

import com.sap.ai.sdk.orchestration.OrchestrationClient;
import com.sap.ai.sdk.orchestration.client.model.LLMModuleConfig;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(OrchestrationSpringProperties.class)
public class OrchestrationAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean
  OrchestrationClient orchestrationClient(OrchestrationSpringProperties properties) {
    var llmConfig =
        LLMModuleConfig.create().modelName(properties.llm().modelName()).modelParams(Map.of());
    if (properties.llm().modelVersion() != null) {
      llmConfig.modelVersion(properties.llm().modelVersion());
    }
    return new OrchestrationClient().withLlmConfig(llmConfig);
  }

  @Bean
  @ConditionalOnMissingBean
  OrchestrationChatModel orchestrationChatModel(OrchestrationClient client) {
    return new OrchestrationChatModel(client);
  }

  @Bean
  @ConditionalOnMissingBean
  ChatClient orchestrationChatModel(OrchestrationChatModel model) {
    return ChatClient.create(model);
  }
}
