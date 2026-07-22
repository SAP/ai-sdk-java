package com.sap.ai.sdk.orchestration;

import lombok.Getter;

import javax.annotation.Nonnull;

@Getter
public final class CacheControl{
    private final String ttl;

    public CacheControl(@Nonnull String ttl) {
        this.ttl = ttl;
    }
}
