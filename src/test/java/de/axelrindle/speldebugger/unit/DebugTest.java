package de.axelrindle.speldebugger.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.axelrindle.speldebugger.model.SpelRequest;
import de.axelrindle.speldebugger.util.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
public class DebugTest extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSimpleStringInterpolation() throws Exception {
        var payload = new SpelRequest(
                "${property}", Map.of("property", "hello world")
        );

        var request = post("/spel")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload));
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result", is("hello world")))
                .andExpect(jsonPath("$.type", is("java.lang.String")))
                .andExpect(jsonPath("$.error", nullValue()));
    }

}
