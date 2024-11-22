package com.homechoice.dto.user;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for Agent Response.
 * This class is used to transfer agent response data between processes.
 *
 * @since 1.0
 */
@Data
@Builder
public class AgentResponseDTO {

    /**
     * Name of the agent.
     */
    private String name;

    /**
     * Email address of the agent.
     */
    private String email;

    /**
     * Phone number of the agent.
     */
    private String phone;
}
