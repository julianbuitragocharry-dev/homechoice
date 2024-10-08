package com.homechoice.controllers.users;

import com.homechoice.dto.users.AgentDTO;
import com.homechoice.services.users.AgentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("agents")
@AllArgsConstructor
public class AgentController {
    private final AgentService agentService;

    @GetMapping("{id}")
    public AgentDTO getById(@PathVariable Integer id) {
        return agentService.getById(id);
    }
}
