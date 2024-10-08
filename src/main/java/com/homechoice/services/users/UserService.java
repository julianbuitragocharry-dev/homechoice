package com.homechoice.services.users;

import com.homechoice.entities.properties.Property;
import com.homechoice.entities.users.Rol;
import com.homechoice.entities.users.User;
import com.homechoice.repositories.properties.PropertyRepository;
import com.homechoice.repositories.users.RolRepository;
import com.homechoice.repositories.users.UserRepository;
import com.homechoice.dto.users.UserDTO;
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

    public List<UserResponseDTO> getAll() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<User> getById(Integer id) {
        return userRepository.findById(id);
    }

    public User createUser(UserDTO userDTO) {
        User user = toEntity(userDTO);
        return userRepository.save(user);
    }

    public User update(Integer id, UserDTO userDTO) {
        User userDB = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("¡User not found!"));

        User updatedUser = toEntity(userDTO);

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

    // Aux functions

    private User toEntity(UserDTO userDTO) {
        return User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .phone(userDTO.getPhone())
                .address(userDTO.getAddress())
                .nit(userDTO.getNit())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .roles(getRolesByIds(userDTO.getRoles()))
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

    private List<Rol> getRolesByIds(List<Integer> roleIds) {
        return roleIds.stream()
                .map(roleId -> rolRepository.findById(roleId)
                        .orElseThrow(() -> new EntityNotFoundException("¡Rol not found!")))
                .collect(Collectors.toList());
    }
}
