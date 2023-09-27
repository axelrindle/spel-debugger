package de.axelrindle.speldebugger.configuration;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.json.JacksonJsonHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "meilisearch", name = "enabled", havingValue = "true")
@Slf4j
public class SearchConfiguration {

    @Value("${meilisearch.host}")
    private String host;

    @Value("${meilisearch.token}")
    private String token;

    @Bean
    public Client meiliClient() {
        JsonMapper mapper = JsonMapper.builder()
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
                .build();

        var config = new Config(host, token, new JacksonJsonHandler(mapper));
        Client client = new Client(config);
        try {
            var healthy = client.isHealthy();
            if (!healthy) {
                throw new MeilisearchException("Meilisearch instance at " + host + " is unhealthy!");
            }
        } catch (MeilisearchException e) {
            log.error("Failed connecting to Meilisearch! Search disabled.");
            return null;
        }
        return client;
    }

}
