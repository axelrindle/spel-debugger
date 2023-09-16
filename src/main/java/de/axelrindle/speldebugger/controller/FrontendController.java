package de.axelrindle.speldebugger.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Hidden
public class FrontendController {

    @GetMapping("/")
    @Operation(hidden = true)
    public String index() {
        return "index.html";
    }

}
