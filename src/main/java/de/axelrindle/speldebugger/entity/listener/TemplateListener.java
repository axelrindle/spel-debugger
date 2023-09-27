package de.axelrindle.speldebugger.entity.listener;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import de.axelrindle.speldebugger.entity.Template;
import io.swagger.v3.core.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.bson.json.JsonWriterSettings;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "meilisearch", name = "enabled", havingValue = "true")
@Slf4j
public class TemplateListener extends AbstractMongoEventListener<Template> {

    public static final String INDEX_NAME = "templates";

    private static final JsonWriterSettings jsonWriterSettings = JsonWriterSettings.builder()
            .objectIdConverter((value, writer) -> writer.writeString(value.toString()))
            .build();

    @Autowired
    private Client client;

    @Override
    public void onAfterSave(@NotNull AfterSaveEvent<Template> event) {
        try {
            var templates = client.index(INDEX_NAME);

            var id = event.getDocument().getObjectId("_id").toString();
            var json = event.getDocument().toJson(jsonWriterSettings);
            log.debug("Indexing document {}: {}", id, json);
            templates.addDocuments(json, "_id");

            log.debug("Indexed document {}", id);
        } catch (MeilisearchException e) {
            log.error("Failed persisting document to Meilisearch!", e);
        }
    }

    @Override
    public void onAfterDelete(@NotNull AfterDeleteEvent<Template> event) {
        try {
            var templates = client.index(INDEX_NAME);
            templates.deleteDocument(event.getDocument().getObjectId("_id").toString());
            log.debug("Deleted document {}", event.getDocument().getObjectId("_id").toString());
        } catch (MeilisearchException e) {
            log.error("Failed deleting document from Meilisearch!", e);
        }
    }


}
