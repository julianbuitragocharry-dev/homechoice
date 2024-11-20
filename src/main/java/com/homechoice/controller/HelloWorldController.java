package com.homechoice.controller;

import com.homechoice.dto.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Hello World", description = "Hello World API for Testing Deployment")
@RestController
@RequestMapping("api/test/public")
@AllArgsConstructor
public class HelloWorldController {
    @Operation(summary = "Get Hello World message",
            description = "Returns a simple hello world message with the current timestamp.")
    @GetMapping
    public MessageResponse hello() {
        return new MessageResponse(
                HttpStatus.OK.value(),
                "Hello World!",
                LocalDateTime.now()
        );
    }
}
