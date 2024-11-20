package com.homechoice.security.auth;

import com.homechoice.security.jwt.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Authentication", description = "API for user authentication and authorization")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("login")
    @Operation(summary = "Login", description = "Authenticate the user and return a JWT token.")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("verify-token")
    @Operation(summary = "Verify Token", description = "Verify if the provided JWT token is expired or valid.")
    public boolean verifyToken(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        return !jwtService.isTokenExpired(token);
    }

    @GetMapping("roles")
    @Operation(summary = "Get Roles", description = "Retrieve the roles of the currently authenticated user.")
    public List<String> getRoles() {
        return authService.getAuthenticatedUserRoles();
    }
}
