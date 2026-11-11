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

  /**
   * Returns whether the harm category was detected.
   *
   * @return {@code true} if harm was detected, {@code false} otherwise.
   */
  public boolean isHarm() {
    return harm != null && Boolean.TRUE.equals(harm.verdict);
  }

  /**
   * Returns whether the violence category was detected.
   *
   * @return {@code true} if violence was detected, {@code false} otherwise.
   */
  public boolean isViolence() {
    return violence != null && Boolean.TRUE.equals(violence.verdict);
  }

  /**
   * Returns whether jailbreak was detected.
   *
   * @return {@code true} if jailbreak was detected, {@code false} otherwise.
   */
  public boolean isJailbreak() {
    return jailbreak != null && Boolean.TRUE.equals(jailbreak.verdict);
  }

  /**
   * Returns whether social bias was detected.
   *
   * @return {@code true} if social bias was detected, {@code false} otherwise.
   */
  public boolean isSocialBias() {
    return socialBias != null && Boolean.TRUE.equals(socialBias.verdict);
  }

  /**
   * Returns whether profanity was detected.
   *
   * @return {@code true} if profanity was detected, {@code false} otherwise.
   */
  public boolean isProfanity() {
    return profanity != null && Boolean.TRUE.equals(profanity.verdict);
  }

  /**
   * Returns whether sexual content was detected.
   *
   * @return {@code true} if sexual content was detected, {@code false} otherwise.
   */
  public boolean isSexualContent() {
    return sexualContent != null && Boolean.TRUE.equals(sexualContent.verdict);
  }

  /**
   * Returns whether unethical behavior was detected.
   *
   * @return {@code true} if unethical behavior was detected, {@code false} otherwise.
   */
  public boolean isUnethicalBehavior() {
    return unethicalBehavior != null && Boolean.TRUE.equals(unethicalBehavior.verdict);
  }
}
