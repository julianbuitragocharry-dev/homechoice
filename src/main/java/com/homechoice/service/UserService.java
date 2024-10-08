package com.homechoice.service;

import com.homechoice.persistence.entity.user.Rol;
import com.homechoice.persistence.entity.user.User;
import com.homechoice.persistence.repository.user.RolRepository;
import com.homechoice.persistence.repository.user.UserRepository;
import com.homechoice.presentation.dto.UserDTO;
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

    public List<User> getAll() {
        return userRepository.findAll();
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
        userRepository.deleteById(id);
        return "User deleted";
    }

    private User toEntity(UserDTO userDTO) {
        List<Rol> roles = userDTO.getRoles().stream()
                .map(roleId -> rolRepository.findById(roleId)
                        .orElseThrow(() -> new EntityNotFoundException("¡Rol not found!")))
                .collect(Collectors.toList());

        return User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .phone(userDTO.getPhone())
                .address(userDTO.getAddress())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .roles(roles)
                .build();
    }
}
