package com.homechoice.controller;

import com.homechoice.dto.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Controller class for handling Hello World API requests.
 * This class provides an endpoint to return a simple hello world message.
 *
 * @see MessageResponse
 */
@RestController
@RequestMapping("api/test/public")
@AllArgsConstructor
public class HelloWorldController {

    /**
     * Endpoint to get a Hello World message.
     *
     * @return a MessageResponse containing the status code, message, and current timestamp
     * @see MessageResponse
     */
    @GetMapping
    public MessageResponse hello() {
        return new MessageResponse(
                HttpStatus.OK.value(),
                "Hello World!",
                LocalDateTime.now()
        );
    }
}
