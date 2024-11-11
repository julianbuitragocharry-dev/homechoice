package com.homechoice.controller;

import com.homechoice.dto.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("api/test/public")
@AllArgsConstructor
public class HelloWorldController {

    @GetMapping("hello")
    public MessageResponse hello() {
        return new MessageResponse(
                HttpStatus.OK.value(),
                "Hello World!",
                LocalDateTime.now()
        );
    }
}
