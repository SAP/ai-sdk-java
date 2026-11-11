package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nullable;
import lombok.NoArgsConstructor;

/** The result of a single Granite Guardian 4.1 category check. */
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraniteGuardian41CategoryResult {

  /** Whether this category was detected. */
  @JsonProperty("verdict")
  @Nullable
  Boolean verdict;
}
