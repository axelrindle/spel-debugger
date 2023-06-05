package de.axelrindle.speldebugger.controller;

import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DebugController {

    private final SpelExpressionParser parser = new SpelExpressionParser();

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PostMapping("/spel")
    public String processSpelRequest(@RequestParam String spel) {
        var result = parser.parseRaw(spel).getValue();
        return result != null ? result.toString() : "null";
    }
}
