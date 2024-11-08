package com.homechoice.controllers;

import com.homechoice.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test/public")
@AllArgsConstructor
public class HelloWorldController {

    @GetMapping("hello")
    public ApiResponse hello() {
        return new ApiResponse("hello");
    }
}
