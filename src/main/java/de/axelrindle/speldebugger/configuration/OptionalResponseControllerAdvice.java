 package de.axelrindle.speldebugger.configuration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Optional;

@ControllerAdvice
public class OptionalResponseControllerAdvice<T> implements ResponseBodyAdvice<Optional<T>> {

    @Override
    public boolean supports(@NotNull MethodParameter returnType,
                            @NotNull Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getParameterType().equals(Optional.class);
    }

    @Override
    public Optional<T> beforeBodyWrite(@Nullable Optional<T> body,
                                       @NotNull MethodParameter returnType,
                                       @NotNull MediaType selectedContentType,
                                       @NotNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                       @NotNull ServerHttpRequest request,
                                       @NotNull ServerHttpResponse response) {
        //noinspection OptionalAssignedToNull
        assert body != null;
        if (body.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found: " + request.getURI());
        }

        return body;
    }

}