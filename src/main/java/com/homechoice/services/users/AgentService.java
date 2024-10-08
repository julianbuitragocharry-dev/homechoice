package com.homechoice.services.users;

import com.homechoice.entities.users.User;
import com.homechoice.repositories.users.UserRepository;
import com.homechoice.dto.users.AgentDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AgentService {
    private final UserRepository userRepository;

    public AgentDTO getById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("¡User not found!"));
        return toDTO(user);
    }

    private AgentDTO toDTO(User user) {
         return AgentDTO.builder()
                 .name(user.getFirstName() + " " + user.getLastName())
                 .phone(user.getPhone())
                 .email(user.getEmail())
                 .build();
    }
}
