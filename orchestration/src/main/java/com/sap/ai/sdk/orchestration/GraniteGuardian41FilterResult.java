package com.sap.ai.sdk.orchestration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nullable;
import lombok.NoArgsConstructor;

/** The result of a Granite Guardian 4.1 filter check, as returned in the exception details. */
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraniteGuardian41FilterResult {

  @JsonProperty("harm")
  @Nullable
  GraniteGuardian41CategoryResult harm;

  @JsonProperty("violence")
  @Nullable
  GraniteGuardian41CategoryResult violence;

  @JsonProperty("jailbreak")
  @Nullable
  GraniteGuardian41CategoryResult jailbreak;

  @JsonProperty("social_bias")
  @Nullable
  GraniteGuardian41CategoryResult socialBias;

  @JsonProperty("profanity")
  @Nullable
  GraniteGuardian41CategoryResult profanity;

  @JsonProperty("sexual_content")
  @Nullable
  GraniteGuardian41CategoryResult sexualContent;

  @JsonProperty("unethical_behavior")
  @Nullable
  GraniteGuardian41CategoryResult unethicalBehavior;

  /** Returns {@code true} if the harm category was detected. */
  public boolean isHarm() {
    return harm != null && Boolean.TRUE.equals(harm.verdict);
  }

  /** Returns {@code true} if the violence category was detected. */
  public boolean isViolence() {
    return violence != null && Boolean.TRUE.equals(violence.verdict);
  }

  /** Returns {@code true} if jailbreak was detected. */
  public boolean isJailbreak() {
    return jailbreak != null && Boolean.TRUE.equals(jailbreak.verdict);
  }

  /** Returns {@code true} if social bias was detected. */
  public boolean isSocialBias() {
    return socialBias != null && Boolean.TRUE.equals(socialBias.verdict);
  }

  /** Returns {@code true} if profanity was detected. */
  public boolean isProfanity() {
    return profanity != null && Boolean.TRUE.equals(profanity.verdict);
  }

  /** Returns {@code true} if sexual content was detected. */
  public boolean isSexualContent() {
    return sexualContent != null && Boolean.TRUE.equals(sexualContent.verdict);
  }

  /** Returns {@code true} if unethical behavior was detected. */
  public boolean isUnethicalBehavior() {
    return unethicalBehavior != null && Boolean.TRUE.equals(unethicalBehavior.verdict);
  }
}
