package com.homechoice.controller.user;

import com.homechoice.dto.user.AgentDTO;
import com.homechoice.dto.user.AgentResponseDTO;
import com.homechoice.dto.user.UserDTO;
import com.homechoice.dto.user.UserResponseDTO;
import com.homechoice.dto.MessageResponse;
import com.homechoice.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

import java.time.LocalDateTime;

@Tag(name = "User Management", description = "API for managing users and agents")
@RestController
@RequestMapping("api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("public/agents/{id}")
    @Operation(summary = "Get agent by ID",
            description = "Retrieves agent information by their ID.")
    public AgentResponseDTO getAgentById(@PathVariable Integer id) {
        return userService.getAgentById(id);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping
    @Operation(summary = "Get all users",
            description = "Retrieves a paginated list of all users with optional NIT filter.")
    public Page<?> getAllUsers(
        @RequestParam(required = false) String nit,
        @RequestParam(defaultValue = "0") Integer page,
        @RequestParam(defaultValue = "6") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllUsers(nit, pageable);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("agents")
    @Operation(summary = "Get all agents",
            description = "Retrieves a paginated list of all agents with optional NIT filter.")
    public Page<?> getAllAgents(
            @RequestParam(required = false) String nit,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "6") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return userService.getAllAgents(nit, pageable);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("{id}")
    @Operation(summary = "Get user by ID",
            description = "Retrieves a user by their ID.")
    public UserDTO getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new user",
            description = "Creates a new user with the provided data.")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO dto) {
        UserResponseDTO response = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("agents")
    @Operation(summary = "Create a new agent",
            description = "Creates a new agent with the provided data.")
    public ResponseEntity<UserResponseDTO> createAgent(@RequestBody AgentDTO dto) {
        UserResponseDTO response = userService.createAgent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PutMapping("{id}")
    @Operation(summary = "Update a user",
            description = "Updates an existing user with the provided data by their ID.")
    public MessageResponse updateUser(@PathVariable Integer id, @RequestBody UserDTO request) {
        userService.updateUser(request, id);
        return new MessageResponse(
                HttpStatus.OK.value(),
                "User updated successfully",
                LocalDateTime.now()
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("agents/{id}")
    @Operation(summary = "Update an agent",
            description = "Updates an existing agent with the provided data by their ID.")
    public MessageResponse updateAgent(@PathVariable Integer id, @RequestBody AgentDTO request) {
        userService.updateAgent(request, id);
        return new MessageResponse(
                HttpStatus.OK.value(),
                "Agent updated successfully",
                LocalDateTime.now()
        );
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @DeleteMapping("{id}")
    @Operation(summary = "Delete a user",
            description = "Deletes a user by their ID.")
    public MessageResponse deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return new MessageResponse(
                HttpStatus.OK.value(),
                "User deleted successfully",
                LocalDateTime.now()
        );
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("agents/{id}")
    @Operation(summary = "Delete an agent",
            description = "Deletes an agent by their ID.")
    public MessageResponse deleteAgent(@PathVariable Integer id) {
        userService.deleteAgent(id);
        return new MessageResponse(
                HttpStatus.OK.value(),
                "Agent deleted successfully",
                LocalDateTime.now()
        );
    }
}
