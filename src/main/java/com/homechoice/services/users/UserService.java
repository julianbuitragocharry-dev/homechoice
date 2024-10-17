package com.homechoice.services.users;

import com.homechoice.dto.users.AgentDTO;
import com.homechoice.dto.users.AgentResponseDTO;
import com.homechoice.dto.users.UserDTO;
import com.homechoice.dto.users.UserResponseDTO;
import com.homechoice.entities.properties.Property;
import com.homechoice.entities.users.Rol;
import com.homechoice.entities.users.User;
import com.homechoice.repositories.properties.PropertyRepository;
import com.homechoice.repositories.users.UserRepository;
import com.homechoice.services.users.auxiliaries.RolService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final RolService rolService;
    private final PasswordEncoder passwordEncoder;

    // PUBLIC
    public AgentResponseDTO getAgentById(Integer id) {
        User user = findById(id);

        boolean isAgent = user.getRoles().stream()
                .anyMatch(rol -> rol.getRol().equals("AGENT"));

        if (!isAgent) {
            throw new IllegalArgumentException("User is not an agent");
        }

        return toAgentDTO(findById(id));
    }

    // SUPER_ADMIN
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

    // ADMIN AND SUPER_ADMIN
    public UserDTO getUserById(Integer id) {
        User user = findById(id);
        return toDTO(user);
    }

    // SUPER_ADMIN
    public UserResponseDTO createUser(UserDTO dto) {
        User user = toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
        return toResponseDTO(user);
    }

    // ADMIN
    public UserResponseDTO createAgent(AgentDTO dto) {
        User user = toEntity(dto);
        List<String> roles = Collections.singletonList("AGENT");
        user.setRoles(rolService.getByRolesNames(roles));
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
        return toResponseDTO(user);
    }

    // SUPER_ADMIN
    public void updateUser(UserDTO dto, Integer id) {
        User user = findById(id);

        List<String> oldRoles = user.getRoles().stream()
                .map(Rol::getRol)
                .toList();
        List<String> newRoles = dto.getRoles();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setNit(dto.getNit());
        user.setEmail(dto.getEmail());
        user.setRoles(rolService.getByRolesNames(newRoles));

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (oldRoles.contains("AGENT") && !newRoles.contains("AGENT")) {
            removeProperties(user);
        }

        userRepository.save(user);
    }

    //ADMIN
    public void updateAgent(AgentDTO dto, Integer id) {
        User user = findById(id);
        boolean isAgent = user.getRoles().stream()
                .anyMatch(rol -> rol.getRol().equals("AGENT"));

        if (!isAgent) {
            throw new EntityNotFoundException("Agent not found");
        }

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        user.setNit(dto.getNit());
        user.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userRepository.save(user);
    }

    // SUPER_ADMIN
    public void deleteUser(Integer id) {
        User user = findById(id);

        removeProperties(user);
        userRepository.deleteById(id);
    }

    // ADMIN
    public void deleteAgent(Integer id) {
        User user = findById(id);
        boolean isAgent = user.getRoles().stream()
                        .anyMatch(rol -> rol.getRol().equals("AGENT"));

        if (!isAgent) {
            throw new EntityNotFoundException("Agent not found");
        }

        removeProperties(user);

        if (user.getRoles().size() == 1) {
            userRepository.deleteById(id);
        } else {
            user.setRoles(user.getRoles().stream()
                    .filter(rol -> !rol.getRol().equals("AGENT"))
                    .collect(Collectors.toList()));
            userRepository.save(user);
        }
    }

    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private void removeProperties(User user) {
        List<Property> properties = propertyRepository.findByAgent(user);
        for (Property property : properties) {
            property.setAgent(null);
            propertyRepository.save(property);
        }
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
                .password("")
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

    private User toEntity(UserDTO dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .nit(dto.getNit())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .roles(rolService.getByRolesNames(dto.getRoles()))
                .build();
    }

    private User toEntity(AgentDTO dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .nit(dto.getNit())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }
}
