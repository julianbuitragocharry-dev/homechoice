package com.homechoice.service.user;

import com.homechoice.dto.user.AgentDTO;
import com.homechoice.dto.user.AgentResponseDTO;
import com.homechoice.dto.user.UserDTO;
import com.homechoice.dto.user.UserResponseDTO;
import com.homechoice.model.property.Property;
import com.homechoice.model.user.Rol;
import com.homechoice.model.user.User;
import com.homechoice.repository.property.PropertyRepository;
import com.homechoice.repository.user.UserRepository;
import com.homechoice.security.auth.AuthService;
import com.homechoice.service.user.auxiliary.RolService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for managing user and agent data and operations, such as retrieving,
 * creating, updating, and deleting users, as well as encoding passwords
 * and assigning properties.
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final RolService rolService;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    /**
     * Retrieves a paginated list of all users. Filters by NIT if provided.
     * Only accessible by SUPER\_ADMIN.
     *
     * @param nit the NIT filter (can be null)
     * @param pageable pagination details
     * @return a paginated list of UserResponseDTOs
     */
    public Page<UserResponseDTO> getAllUsers(String nit, Pageable pageable) {
        Integer authenticatedId = authService.getAuthenticatedUserId();
        if (nit != null) { nit = "%" + nit + "%"; }
        return userRepository.findAll(nit, authenticatedId, pageable)
                .map(this::toResponseDTO);
    }

    /**
     * Retrieves a user by ID. Accessible by both ADMIN and SUPER\_ADMIN.
     *
     * @param id the ID of the user
     * @return UserDTO containing user information
     */
    public UserDTO getUserById(Integer id) {
        User user = findById(id);
        return toDTO(user);
    }

    /**
     * Creates a new user. Only accessible by SUPER\_ADMIN.
     *
     * @param dto UserDTO containing user details
     * @return UserResponseDTO of the created user
     */
    public UserResponseDTO createUser(UserDTO dto) {
        List<String> filteredRoles = dto.getRoles().stream()
                .filter(role -> !role.equalsIgnoreCase("SUPER_ADMIN"))
                .collect(Collectors.toList());
        dto.setRoles(filteredRoles);

        User user = toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
        return toResponseDTO(user);
    }

    /**
     * Updates a user's information, including roles. Removes properties from
     * the user if they lose the "AGENT" role. Only accessible by SUPER\_ADMIN.
     *
     * @param dto UserDTO with updated user information
     * @param id the ID of the user to update
     */
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

    /**
     * Deletes a user by ID. Removes their properties if they are assigned any.
     * Only accessible by SUPER\_ADMIN.
     *
     * @param id the ID of the user to delete
     */
    public void deleteUser(Integer id) {
        User user = findById(id);

        removeProperties(user);
        userRepository.deleteById(id);
    }

    /**
     * Retrieves a paginated list of all agents. Filters by NIT if provided.
     * Only accessible by ADMIN.
     *
     * @param nit the NIT filter (can be null)
     * @param pageable pagination details
     * @return a paginated list of UserResponseDTOs for agents
     */
    public Page<UserResponseDTO> getAllAgents(String nit, Pageable pageable) {
        Integer authenticatedId = authService.getAuthenticatedUserId();
        if (nit != null) { nit = "%" + nit + "%"; }
        return userRepository.findByRolesRol("AGENT", nit, authenticatedId, pageable)
                .map(this::toResponseDTO);
    }

    /**
     * Retrieves an agent by ID. Throws an exception if the user is not an agent.
     *
     * @param id the ID of the agent
     * @return AgentResponseDTO containing agent information
     * @throws IllegalArgumentException if the user is not an agent
     */
    public AgentResponseDTO getAgentById(Integer id) {
        User user = findById(id);

        boolean isAgent = user.getRoles().stream()
                .anyMatch(rol -> rol.getRol().equals("AGENT"));

        if (!isAgent) {
            throw new IllegalArgumentException("User is not an agent");
        }

        return toAgentDTO(findById(id));
    }

    /**
     * Creates a new agent with the "AGENT" role. Only accessible by ADMIN.
     *
     * @param dto AgentDTO containing agent details
     * @return UserResponseDTO of the created agent
     */
    public UserResponseDTO createAgent(AgentDTO dto) {
        User user = toEntity(dto);
        List<String> roles = Collections.singletonList("AGENT");
        user.setRoles(rolService.getByRolesNames(roles));
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);
        return toResponseDTO(user);
    }

    /**
     * Updates an agent's information. Only accessible by ADMIN.
     *
     * @param dto AgentDTO with updated agent information
     * @param id the ID of the agent to update
     * @throws EntityNotFoundException if the user is not an agent
     */
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

    /**
     * Deletes an agent by ID. Removes their properties if they are assigned any.
     * Only accessible by ADMIN.
     *
     * @param id the ID of the agent to delete
     * @throws EntityNotFoundException if the user is not an agent
     */
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

    /**
     * Finds a user by ID.
     *
     * @param id the ID of the user
     * @return the User entity
     * @throws EntityNotFoundException if the user is not found
     */
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    /**
     * Removes all properties assigned to a user.
     *
     * @param user the User whose properties are to be removed
     */
    private void removeProperties(User user) {
        List<Property> properties = propertyRepository.findByAgent(user);
        for (Property property : properties) {
            property.setAgent(null);
            propertyRepository.save(property);
        }
    }

    /**
     * Converts a User entity to a UserResponseDTO.
     *
     * @param user the User entity to convert
     * @return a UserResponseDTO containing the user's details
     */
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

    /**
     * Converts a User entity to a UserDTO.
     * The password is set to an empty string for security purposes.
     *
     * @param user the User entity to convert
     * @return a UserDTO containing the user's details, excluding the password
     */
    public UserDTO toDTO(User user) {
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

    /**
     * Converts a User entity to an AgentResponseDTO.
     * The response includes the agent's name, email, and phone.
     *
     * @param user the User entity to convert
     * @return an AgentResponseDTO containing the agent's name, email, and phone
     */
    private AgentResponseDTO toAgentDTO(User user) {
        return AgentResponseDTO.builder()
                .name(user.getFirstName() + " " + user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    /**
     * Converts a UserDTO to a User entity.
     * The entity is populated with data from the DTO, including the roles.
     *
     * @param dto the UserDTO containing the user's details
     * @return a User entity with data from the DTO
     */
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

    /**
     * Converts an AgentDTO to a User entity.
     * The entity is populated with data from the AgentDTO.
     *
     * @param dto the AgentDTO containing the agent's details
     * @return a User entity with data from the AgentDTO
     */
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
