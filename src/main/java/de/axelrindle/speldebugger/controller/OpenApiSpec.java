package de.axelrindle.speldebugger.controller;

import de.axelrindle.speldebugger.model.SpelRequest;
import de.axelrindle.speldebugger.model.SpelResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

/**
 * Provides the OpenAPI specs for most of the controllers.
 */
public class OpenApiSpec {

    @Tag(name = "Processor")
    public interface DebugController {

        @Operation(summary = "Processes a SpEL expression.")
        @RequestBody(content = @Content(examples = @ExampleObject(
                """
                {
                    "spel": "#{T(java.time.LocalDate).parse('${property.name}')}",
                    "context": {
                        "property.name": "2023-05-06"
                    }
                }
                """
        )))
        @ApiResponses({
                @ApiResponse(
                        responseCode = "200",
                        description = "The result data."
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Something went wrong. Check the 'error' attribute."
                )
        })
        ResponseEntity<SpelResponse> processSpelRequest(SpelRequest form);
    }

}
