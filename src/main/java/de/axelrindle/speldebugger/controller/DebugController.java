package de.axelrindle.speldebugger.controller;

import de.axelrindle.speldebugger.model.SpelRequest;
import de.axelrindle.speldebugger.model.SpelResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.lang.reflect.Constructor;
import java.util.Map;

@Controller
@Slf4j
public class DebugController {

    private final SpelExpressionParser parser = new SpelExpressionParser();

    @Autowired
    private StandardServletEnvironment environment;

    /**
     * Processes a SpEL expression mimicking the behavior of the {@link Value} annotation.
     *
     * @param form The {@link SpelRequest} to process.
     * @return A {@link SpelResponse} indicating the processing result.
     * @see org.springframework.expression.common.TemplateParserContext
     */
    @PostMapping("/spel")
    public ResponseEntity<SpelResponse> processSpelRequest(@RequestBody SpelRequest form) {
        try {
            StandardEnvironment environment = makeEnvironment(form.context());
            String spel = environment.resolveRequiredPlaceholders(form.spel());
            if (spel.startsWith("#{")) {
                // the #{} syntax is used by the @Value annotation to indicate
                // an incoming SpEL expression
                spel = spel.substring(2, spel.length() - 1);
            }

            log.debug("Processing SpEL expression: {}", spel);

            var result = parser.parseExpression(spel).getValue();
            if (result == null) {
                return ResponseEntity.ok(new SpelResponse("null", "null", null));
            }

            return ResponseEntity.ok(new SpelResponse(result.toString(), result.getClass().getName(), null));
        } catch (Exception e) {
            var message = e.getClass().getName() + ": " + e.getMessage();
            log.debug("SpEL parsing error: %s".formatted(message), e);
            return ResponseEntity.badRequest().body(new SpelResponse(null, null, message));
        }
    }

    /**
     * Construct a custom environment with a context hashmap to simulate the keys of the map
     * being available as runtime properties.
     *
     * @param context The properties to add.
     * @return A {@link StandardEnvironment} instance for property replacement.
     * @throws ReflectiveOperationException A required constructor is not publicly accessible. Thrown in case
     *                                      the constructor invocation failed.
     */
    @NotNull
    private StandardEnvironment makeEnvironment(Map<String, Object> context) throws ReflectiveOperationException {
        Constructor<StandardEnvironment> constructor = StandardEnvironment.class.getDeclaredConstructor(MutablePropertySources.class);
        constructor.setAccessible(true);

        MutablePropertySources propertySources = this.environment.getPropertySources();
        propertySources.addLast(new MapPropertySource("context", context));

        return constructor.newInstance(propertySources);
    }

}
