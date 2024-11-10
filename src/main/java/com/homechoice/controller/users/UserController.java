package com.homechoice.controller.users;

import com.homechoice.dto.user.AgentDTO;
import com.homechoice.dto.user.AgentResponseDTO;
import com.homechoice.dto.user.UserDTO;
import com.homechoice.dto.user.UserResponseDTO;
import com.homechoice.response.ApiResponse;
import com.homechoice.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Page<UserResponseDTO> getAllUsers(
        @RequestParam(required = false) String nit,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "6") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllUsers(nit, pageable);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("agents")
    public Page<UserResponseDTO> getAllAgents(
            @RequestParam(required = false) String nit,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "6") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllAgents(nit, pageable);
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
