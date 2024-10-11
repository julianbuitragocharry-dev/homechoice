package com.homechoice.controllers.users;

import com.homechoice.dto.users.AgentResponseDTO;
import com.homechoice.dto.users.AgentsResponseDTO;
import com.homechoice.dto.users.UserRequestDTO;
import com.homechoice.entities.users.User;
import com.homechoice.services.users.auxiliaries.AgentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("agents")
@AllArgsConstructor
public class AgentController {
    private final AgentService agentService;

    @GetMapping
    public List<AgentsResponseDTO> getAgents() {
        return agentService.getAgents();
    }

    @GetMapping("{id}")
    public AgentResponseDTO getAgentById(@PathVariable Integer id) {
        return agentService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createAgent(@RequestBody UserRequestDTO userRequestDTO) {
        return agentService.createAgent(userRequestDTO);
    }

    @PutMapping("{id}")
    public User updateAgent(@PathVariable Integer id, @RequestBody UserRequestDTO userRequestDTO) {
        return agentService.updateAgent(id, userRequestDTO);
    }
}
