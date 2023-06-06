package de.axelrindle.speldebugger.model;

import jakarta.annotation.Nullable;

public record SpelResponse(
        @Nullable Object result,
        @Nullable String type,
        @Nullable String error
) { }
