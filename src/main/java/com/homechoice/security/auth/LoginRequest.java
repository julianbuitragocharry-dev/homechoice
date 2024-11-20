package com.homechoice.security.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request object containing user credentials for authentication.")
public class LoginRequest {

    @Schema(description = "User's email address for login", example = "user@example.com")
    String email;

    @Schema(description = "User's password for login", example = "strongpassword123")
    String password;
}
