package de.axelrindle.speldebugger.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY;

@Document
@Data
public final class Template {

    @Id
    @JsonAlias("_id")
    @Schema(accessMode = READ_ONLY)
    @ReadOnlyProperty
    private String id;

    private String name;
    private String expression;
    private List<Variable> variables = new ArrayList<>();

    public Template apply(@NotNull Template other) {
        this.applier(this::setName, this::getName, other::getName);
        this.applier(this::setExpression, this::getExpression, other::getExpression);
        this.applier(this::setVariables, this::getVariables, other::getVariables);

        return this;
    }

    private <T> void applier(@NotNull Consumer<T> setter,
                             @NotNull Supplier<T> getterThis,
                             @NotNull Supplier<T> getterOther) {
        setter.accept(Optional.ofNullable(getterOther.get()).orElse(getterThis.get()));
    }

}
