package de.axelrindle.speldebugger.model;

import java.util.Map;

public record SpelRequest(
        String spel,
        Map<String, Object> context
) {

    @Override
    public Map<String, Object> context() {
        if (context == null) {
            return Map.of();
        }

        return context;
    }

}
