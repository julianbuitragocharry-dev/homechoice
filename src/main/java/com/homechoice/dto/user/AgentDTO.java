package com.homechoice.dto.user;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for Agent.
 * This class is used to transfer agent data between processes.
 *
 * @since 1.0
 */
@Data
@Builder
public class AgentDTO {

    /**
     * Unique identifier of the agent.
     */
    private Integer id;

    /**
     * First name of the agent.
     */
    private String firstName;

    /**
     * Last name of the agent.
     */
    private String lastName;

    /**
     * Phone number of the agent.
     */
    private String phone;

    /**
     * Address of the agent.
     */
    private String address;

    /**
     * NIT (Tax Identification Number) of the agent.
     */
    private String nit;

    /**
     * Email address of the agent.
     */
    private String email;

    /**
     * Password of the agent.
     */
    private String password;
}
