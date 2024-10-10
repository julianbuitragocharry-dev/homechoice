package com.homechoice.services.users;

import com.homechoice.dto.users.AgentResponseDTO;
import com.homechoice.entities.properties.Property;
import com.homechoice.entities.users.Rol;
import com.homechoice.entities.users.User;
import com.homechoice.repositories.properties.PropertyRepository;
import com.homechoice.repositories.users.RolRepository;
import com.homechoice.repositories.users.UserRepository;
import com.homechoice.dto.users.UserRequestDTO;
import com.homechoice.dto.users.UserResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PropertyRepository propertyRepository;
    private final RolService rolService;

    public List<UserResponseDTO> getAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<User> getById(Integer id) {
        return userRepository.findById(id);
    }

    public User createUser(UserRequestDTO userRequestDTO) {
        User user = toEntity(userRequestDTO);
        return userRepository.save(user);
    }

    public User update(Integer id, UserRequestDTO userRequestDTO) {
        User userDB = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("¡User not found!"));

        User updatedUser = toEntity(userRequestDTO);

        userDB.setFirstName(updatedUser.getFirstName());
        userDB.setLastName(updatedUser.getLastName());
        userDB.setPhone(updatedUser.getPhone());
        userDB.setAddress(updatedUser.getAddress());
        userDB.setEmail(updatedUser.getEmail());
        userDB.setPassword(updatedUser.getPassword());
        userDB.setRoles(updatedUser.getRoles());

        return userRepository.save(userDB);
    }

    public String delete(Integer id) {
        User userToDelete = userRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        List<Property> properties = propertyRepository.findByUser(userToDelete);

        for (Property property : properties) {
            property.setUser(null);
            propertyRepository.save(property);
        }

        userRepository.deleteById(id);
        return "User deleted";
    }

    public List<AgentResponseDTO> getAgents() {
        return userRepository.findByRoles_Rol("AGENT").stream()
                .map(this::toAgent)
                .collect(Collectors.toList());
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    private User toEntity(UserRequestDTO userRequestDTO) {
        return User.builder()
                .firstName(userRequestDTO.getFirstName())
                .lastName(userRequestDTO.getLastName())
                .phone(userRequestDTO.getPhone())
                .address(userRequestDTO.getAddress())
                .nit(userRequestDTO.getNit())
                .email(userRequestDTO.getEmail())
                .password(userRequestDTO.getPassword())
                .roles(rolService.fetchRolesByIds(userRequestDTO.getRolesId()))
                .build();
    }

    private UserResponseDTO toDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .name(user.getFirstName() + " " + user.getLastName())
                .phone(user.getPhone())
                .address(user.getAddress())
                .nit(user.getNit())
                .email(user.getEmail())
                .build();
    }

    private AgentResponseDTO toAgent(User user) {
        return AgentResponseDTO.builder()
                .id(user.getId())
                .name(user.getFirstName() + " " + user.getLastName())
                .build();
    }

}
