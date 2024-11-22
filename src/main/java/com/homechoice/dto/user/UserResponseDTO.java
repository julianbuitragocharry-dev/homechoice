package com.homechoice.dto.user;

import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for User Response.
 * This class is used to transfer user response data between processes.
 *
 * @since 1.0
 */
@Data
@Builder
public class UserResponseDTO {

    /**
     * Unique identifier of the user.
     */
    private Integer id;

    /**
     * First name of the user.
     */
    private String firstName;

    /**
     * Last name of the user.
     */
    private String lastName;

    /**
     * Phone number of the user.
     */
    private String phone;

    /**
     * Address of the user.
     */
    private String address;

    /**
     * NIT (Tax Identification Number) of the user.
     */
    private String nit;

    /**
     * Email address of the user.
     */
    private String email;
}
