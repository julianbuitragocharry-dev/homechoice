package com.homechoice.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request object containing a token for authentication.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenRequest {

    /**
     * Token string used for authentication.
     * Example: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
     */
    private String token;
}
