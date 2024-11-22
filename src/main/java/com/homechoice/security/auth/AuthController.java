package com.homechoice.security.auth;

import com.homechoice.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling authentication and authorization requests.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    /**
     * Authenticates the user and returns a JWT token.
     *
     * @param request the login request containing user credentials
     * @return the authentication response containing the JWT token
     * @see AuthService
     * @see AuthResponse
     * @see LoginRequest
     */
    @PostMapping("login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    /**
     * Verifies if the provided JWT token is expired or valid.
     *
     * @param tokenRequest the token request containing the JWT token
     * @return true if the token is valid, false if it is expired
     * @see JwtService
     * @see TokenRequest
     */
    @PostMapping("verify-token")
    public boolean verifyToken(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        return !jwtService.isTokenExpired(token);
    }

    /**
     * Retrieves the roles of the currently authenticated user.
     *
     * @return a list of roles assigned to the authenticated user
     * @see AuthService
     */
    @GetMapping("roles")
    public List<String> getRoles() {
        return authService.getAuthenticatedUserRoles();
    }
}
