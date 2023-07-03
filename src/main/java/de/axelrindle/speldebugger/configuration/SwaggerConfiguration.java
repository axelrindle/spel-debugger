package de.axelrindle.speldebugger.configuration;

import de.axelrindle.speldebugger.SpelDebuggerApplication;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Autowired
    private BuildProperties buildProperties;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("Debugger for SpEL")
                    .description("A debugger for the Spring Expression Language (SpEL).")
                    .version(buildProperties.getVersion())
                    .license(
                        new License()
                            .identifier("GPLv3")
                            .name("GNU GPLv3")
                            .url("https://www.gnu.org/licenses/gpl-3.0.en.html")
                    )
            )
            .externalDocs(
                new ExternalDocumentation()
                    .url("https://github.com/axelrindle/spel-debugger")
            );
    }
}
