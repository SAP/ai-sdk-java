package com.sap.ai.sdk.core;

import lombok.experimental.StandardException;

/** Exception thrown when a lookup for a deployment ID on the AI Core service failed. */
@StandardException
public class DeploymentResolutionException extends RuntimeException {}
