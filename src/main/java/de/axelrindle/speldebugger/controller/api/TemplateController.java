package de.axelrindle.speldebugger.controller.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import de.axelrindle.speldebugger.controller.OpenApiSpec;
import de.axelrindle.speldebugger.entity.Template;
import de.axelrindle.speldebugger.entity.listener.TemplateListener;
import de.axelrindle.speldebugger.repository.TemplateRepository;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/template")
@Slf4j
public class TemplateController implements OpenApiSpec.TemplateController {

    private static final TypeReference<List<Template>> TYPE_HITS = new TypeReference<>() {
    };

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired(required = false)
    private Client searchClient;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    private void init() throws MeilisearchException, JsonProcessingException {
        if (searchClient == null) return;

        var templates = searchClient.index(TemplateListener.INDEX_NAME);
        var attributes = new String[]{"name", "expression"};
        templates.updateFilterableAttributesSettings(attributes);
        templates.updateSearchableAttributesSettings(attributes);
        templates.updateSortableAttributesSettings(attributes);

        var all = templateRepository.findAll();

        templates.addDocuments(objectMapper.writeValueAsString(all), "_id");

        log.info("Indexed {} template(s) at startup.", all.size());
    }

    @GetMapping("")
    public List<Template> list(@RequestParam(required = false) String query,
                               @RequestParam(required = false) boolean exact) {
        if (exact) {
            return templateRepository.findByName(query).stream().toList();
        }

        if (searchClient != null) {
            try {
                var index = searchClient.index(TemplateListener.INDEX_NAME);
                var result = index.search(query);
                return objectMapper.convertValue(result.getHits(), TYPE_HITS);
            } catch (MeilisearchException e) {
                log.error("Meilisearch request failed!", e);
            }
        }

        if (query != null) {
            return templateRepository.findByNameContains(query);
        }

        return templateRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Template> get(@PathVariable @NotNull String id) {
        return templateRepository.findById(id);
    }

    @PostMapping("")
    public ResponseEntity<Template> create(@RequestBody @NotNull Template template) {
        var existing = templateRepository.findByName(template.getName());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        var created = templateRepository.save(template);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Template> update(@PathVariable @NotNull String id,
                                           @RequestBody @NotNull Template template) {
        var existingOptional = templateRepository.findById(id);
        if (existingOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var existing = existingOptional.get();
        var updated = templateRepository.save(existing.apply(template));

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NotNull String id) {
        var template = templateRepository.findById(id);
        if (template.isPresent()) {
            templateRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

}
