package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** The result of a single Granite Guardian 4.1 category check. */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraniteGuardian41CategoryResult {

  /** Whether this category was detected. */
  @JsonProperty("verdict")
  @Nullable
  Boolean verdict;
}
