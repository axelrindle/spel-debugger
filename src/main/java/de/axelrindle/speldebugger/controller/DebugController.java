package de.axelrindle.speldebugger.controller;

import de.axelrindle.speldebugger.model.SpelRequest;
import de.axelrindle.speldebugger.model.SpelResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.expression.spel.SpelEvaluationException;
import org.springframework.expression.spel.SpelParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.lang.reflect.Constructor;
import java.util.Map;

@Controller
@CrossOrigin(origins = "*")
@Slf4j
public class DebugController {

    private final SpelExpressionParser parser = new SpelExpressionParser();

    @Autowired
    private StandardServletEnvironment environment;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PostMapping("/spel")
    @ResponseBody
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = @ExampleObject("""
            {
            	"spel": "#{T(java.time.LocalDate).parse('${property.name}')}",
            	"context": {
            		"property.name": "2023-05-06"
            	}
            }
            """))
    )
    public SpelResponse processSpelRequest(@RequestBody SpelRequest form) throws ReflectiveOperationException {
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
            var string = result == null ? null : result.toString();
            return new SpelResponse(string, null);
        } catch (IllegalArgumentException | SpelEvaluationException | SpelParseException e) {
            log.error("SpEL parsing error: {}", e.getMessage());
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
            return new SpelResponse(null, e.getMessage());
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
    private StandardEnvironment makeEnvironment(Map<String, Object> context) throws ReflectiveOperationException {
        Constructor<StandardEnvironment> constructor = StandardEnvironment.class.getDeclaredConstructor(MutablePropertySources.class);
        constructor.setAccessible(true);

        MutablePropertySources propertySources = this.environment.getPropertySources();
        propertySources.addLast(new MapPropertySource("context", context));

        return constructor.newInstance(propertySources);
    }

}
