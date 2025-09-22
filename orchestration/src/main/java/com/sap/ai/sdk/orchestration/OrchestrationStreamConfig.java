package com.sap.ai.sdk.orchestration;

import com.sap.ai.sdk.orchestration.model.FilteringStreamOptions;
import com.sap.ai.sdk.orchestration.model.GlobalStreamOptions;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;
import lombok.val;

/**
 * Configuration for orchestration streaming options.
 *
 * @since 1.12.0
 */
@Value
@With
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrchestrationStreamConfig {
  /**
   * Number of characters that should be additionally sent to content filtering services from
   * previous chunks as additional context.
   */
  @Nullable Integer overlap;

  /** Size of the chunks the response will be split into when streaming. */
  @Nullable Integer chunkSize = null;

  /** List of delimiters to use for chunking the response when streaming. */
  @Nullable List<String> delimiters = null;

  @Nullable
  FilteringStreamOptions createFilteringStreamOptions() {
    return overlap == null ? null : FilteringStreamOptions.create().overlap(overlap);
  }

  @Nullable
  GlobalStreamOptions createGlobalStreamOptions() {
    if (chunkSize == null && delimiters == null) {
      return null;
    }
    val opts = GlobalStreamOptions.create();
    Optional.ofNullable(chunkSize).ifPresent(opts::setChunkSize);
    Optional.ofNullable(delimiters).ifPresent(d -> opts.setDelimiters(List.copyOf(d)));
    return opts;
  }
}
