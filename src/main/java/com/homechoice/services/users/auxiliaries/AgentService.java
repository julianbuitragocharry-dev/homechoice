package com.homechoice.services.users.auxiliaries;

import com.homechoice.dto.users.AgentsResponseDTO;
import com.homechoice.entities.users.User;
import com.homechoice.repositories.users.UserRepository;
import com.homechoice.dto.users.AgentResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AgentService {
    private final UserRepository userRepository;

    public List<AgentsResponseDTO> getAgents() {
        return userRepository.findByRoles_Rol("AGENT").stream()
                .map(this::toAgentsDTO)
                .collect(Collectors.toList());
    }

    public AgentResponseDTO getById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("¡User not found!"));
        return toAgentDTO(user);
    }

    private AgentResponseDTO toAgentDTO(User user) {
         return AgentResponseDTO.builder()
                 .name(user.getFirstName() + " " + user.getLastName())
                 .phone(user.getPhone())
                 .email(user.getEmail())
                 .build();
    }

    private AgentsResponseDTO toAgentsDTO(User user) {
        return AgentsResponseDTO.builder()
                .id(user.getId())
                .name(user.getFirstName() + " " + user.getLastName())
                .build();
    }

}
