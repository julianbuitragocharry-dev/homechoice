package com.homechoice.controllers.test;

import com.homechoice.dto.users.UserDTO;
import com.homechoice.dto.users.UserResponseDTO;
import com.homechoice.responses.ApiResponse;
import com.homechoice.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test/public")
@AllArgsConstructor
public class TestController {
    private final UserService userService;

    @GetMapping("hello")
    public ApiResponse hello() {
        return new ApiResponse("hello");
    }

    @PostMapping("add")
    public ResponseEntity<UserResponseDTO> add(@RequestBody UserDTO dto) {
        UserResponseDTO response = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
