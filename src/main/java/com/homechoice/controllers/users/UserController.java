package com.homechoice.controllers.users;

import com.homechoice.dto.users.AgentDTO;
import com.homechoice.dto.users.AgentResponseDTO;
import com.homechoice.dto.users.UserDTO;
import com.homechoice.dto.users.UserResponseDTO;
import com.homechoice.responses.ApiResponse;
import com.homechoice.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("public/agents/{id}")
    public AgentResponseDTO getAgentById(@PathVariable Integer id) {
        return userService.getAgentById(id);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("agents")
    public List<UserResponseDTO> getAllAgents() {
        return userService.getAllAgents();
    }

    @GetMapping("{id}")
    public UserDTO getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO dto) {
        UserResponseDTO response = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("agents")
    public ResponseEntity<UserResponseDTO> createAgent(@RequestBody AgentDTO dto) {
        UserResponseDTO response = userService.createAgent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Integer id, @RequestBody UserDTO request) {
        userService.updateUser(request, id);
        return ResponseEntity.ok(new ApiResponse("User updated"));
    }

    @PutMapping("agents/{id}")
    public ResponseEntity<ApiResponse> updateAgent(@PathVariable Integer id, @RequestBody AgentDTO request) {
        userService.updateAgent(request, id);
        return ResponseEntity.ok(new ApiResponse("Agent updated"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse("User deleted"));
    }

    @DeleteMapping("agents/{id}")
    public ResponseEntity<ApiResponse> deleteAgent(@PathVariable Integer id) {
        userService.deleteAgent(id);
        return ResponseEntity.ok(new ApiResponse("Agent deleted"));
    }
}
