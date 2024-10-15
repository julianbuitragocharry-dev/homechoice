package com.homechoice.services.users;

import com.homechoice.dto.users.AgentResponseDTO;
import com.homechoice.dto.users.UserDTO;
import com.homechoice.dto.users.UserResponseDTO;
import com.homechoice.entities.properties.Property;
import com.homechoice.entities.users.Rol;
import com.homechoice.entities.users.User;
import com.homechoice.repositories.properties.PropertyRepository;
import com.homechoice.repositories.users.RolRepository;
import com.homechoice.repositories.users.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    // PUBLIC
    public AgentResponseDTO getAgentById(Integer id) {
        return toAgentDTO(findById(id));
    }

    // SUPER_ADMIN
    // TODO: Filter out autheticated user to prevent self-modification
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ADMIN
    // TODO: Filter out autheticated user to prevent self-modification
    public List<UserResponseDTO> getAllAgents() {
        return userRepository.findByRoles_Rol("AGENT").stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // TODO: Create and update methods. Delete for admin

    // ADMIN AND SUPER_ADMIN
    public UserDTO getUserById(Integer id) {
        User user = findById(id);
        return toDTO(user);
    }

    // SUPER_ADMIN
    public void deleteUser(Integer id) {
        User user = findById(id);
        List<Property> properties = propertyRepository.findByAgent(user);

        for (Property property : properties) {
            property.setAgent(null);
            propertyRepository.save(property);
        }

        userRepository.deleteById(id);
    }

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private UserResponseDTO toResponseDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .nit(user.getNit())
                .build();
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress())
                .nit(user.getNit())
                .roles(user.getRoles().stream().map(Rol::getRol).collect(Collectors.toList()))
                .build();
    }

    private AgentResponseDTO toAgentDTO(User user) {
        return AgentResponseDTO.builder()
                .name(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}
