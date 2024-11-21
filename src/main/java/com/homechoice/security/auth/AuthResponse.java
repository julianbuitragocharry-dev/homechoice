package com.homechoice.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response object containing an authentication token.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    /**
     * JWT token for accessing secured endpoints.
     * Example: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
     */
    String token;

    /**
     * User associated with the authentication token.
     */
    String user;
}
