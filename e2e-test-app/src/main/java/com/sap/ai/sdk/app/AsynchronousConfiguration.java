package com.sap.ai.sdk.app;

// Spring Boot asynchronous configuration

import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutors;
import java.util.concurrent.Executor;
import javax.annotation.Nonnull;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Implementation that customizes the {@link Executor} instance used when processing @{@link Async}
 * method invocations.
 */
@SuppressWarnings("unused")
@EnableAsync
@Configuration
public class AsynchronousConfiguration implements AsyncConfigurer {
  @Nonnull
  @Override
  public Executor getAsyncExecutor() {
    return ThreadContextExecutors.getExecutor();
  }
}
