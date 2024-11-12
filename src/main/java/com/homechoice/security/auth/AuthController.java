package com.homechoice.security.auth;

import com.homechoice.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/verify-token")
    public boolean verifyToken(@RequestBody TokenRequest tokenRequest) {
        String token = tokenRequest.getToken();
        return !jwtService.isTokenExpired(token);
    }

    @GetMapping("/roles")
    public List<String> getRoles() {
        return authService.getAuthenticatedUserRoles();
    }
}
