package com.homechoice.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request object containing user credentials for authentication.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {

    /**
     * User's email address for login.
     * Example: "user@example.com"
     */
    String email;

    /**
     * User's password for login.
     * Example: "strongpassword123"
     */
    String password;
}
