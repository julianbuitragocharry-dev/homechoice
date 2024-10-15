package com.homechoice.controllers.users;

import com.homechoice.dto.users.AgentResponseDTO;
import com.homechoice.dto.users.UserDTO;
import com.homechoice.dto.users.UserResponseDTO;
import com.homechoice.entities.users.User;
import com.homechoice.services.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("agents/{id}")
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

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }
}
