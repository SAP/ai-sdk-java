package com.sap.ai.sdk.orchestration;

import javax.annotation.Nullable;

public sealed interface CacheablePrompt permits TextItem {

    @Nullable
    CacheControl getCacheControl();
}
