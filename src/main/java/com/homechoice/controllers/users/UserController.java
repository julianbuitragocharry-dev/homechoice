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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("agents")
    public List<UserResponseDTO> getAllAgents() {
        return userService.getAllAgents();
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("{id}")
    public UserDTO getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO dto) {
        UserResponseDTO response = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("agents")
    public ResponseEntity<UserResponseDTO> createAgent(@RequestBody AgentDTO dto) {
        UserResponseDTO response = userService.createAgent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Integer id, @RequestBody UserDTO request) {
        userService.updateUser(request, id);
        return ResponseEntity.ok(new ApiResponse("User updated"));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("agents/{id}")
    public ResponseEntity<ApiResponse> updateAgent(@PathVariable Integer id, @RequestBody AgentDTO request) {
        userService.updateAgent(request, id);
        return ResponseEntity.ok(new ApiResponse("Agent updated"));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse("User deleted"));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("agents/{id}")
    public ResponseEntity<ApiResponse> deleteAgent(@PathVariable Integer id) {
        userService.deleteAgent(id);
        return ResponseEntity.ok(new ApiResponse("Agent deleted"));
    }
}
