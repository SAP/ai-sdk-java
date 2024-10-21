package com.sap.ai.sdk.orchestration.spring;

import com.sap.ai.sdk.orchestration.OrchestrationClient;
import lombok.extern.slf4j.Slf4j;
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
    return new OrchestrationClient().withLlmConfig(properties.llm());
  }

  @Bean
  @ConditionalOnMissingBean
  OrchestrationChatModel orchestrationChatModel(OrchestrationClient client) {
    return new OrchestrationChatModel(client);
  }
  //
  //  @Bean
  //  @ConditionalOnMissingBean
  //  ChatClient orchestrationChatClient(OrchestrationChatModel model) {
  //    return ChatClient.create(model);
  //  }
}
