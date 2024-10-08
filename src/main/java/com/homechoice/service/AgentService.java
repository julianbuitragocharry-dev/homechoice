package com.homechoice.service;

import com.homechoice.persistence.entity.user.User;
import com.homechoice.persistence.repository.user.UserRepository;
import com.homechoice.presentation.dto.AgentDTO;
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
