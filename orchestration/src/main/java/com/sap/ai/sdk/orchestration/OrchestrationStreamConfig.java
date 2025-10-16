package com.sap.ai.sdk.orchestration;

import com.google.common.annotations.Beta;
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
@Beta
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrchestrationStreamConfig {
  /**
   * Number of characters that should be additionally sent to content filtering services from
   * previous chunks as additional context.
   */
  @Nullable Integer filterOverlap;

  /** Size of the chunks the response will be split into when streaming. */
  @Nullable Integer chunkSize;

  /** List of delimiters to use for chunking the response when streaming. */
  @Nullable List<String> delimiters;

  /** Default constructor for OrchestrationStreamConfig. */
  public OrchestrationStreamConfig() {
    this(null, null, null);
  }

  @Nullable
  FilteringStreamOptions createFilteringStreamOptions() {
    return filterOverlap == null ? null : FilteringStreamOptions.create().overlap(filterOverlap);
  }

  @Nullable
  GlobalStreamOptions createGlobalStreamOptions() {
    if (chunkSize == null && delimiters == null) {
      return null;
    }
    val opts = GlobalStreamOptions.create();
    Optional.ofNullable(chunkSize).ifPresent(opts::setChunkSize);
    opts.setDelimiters(delimiters == null ? null : List.copyOf(delimiters));
    return opts;
  }
}
