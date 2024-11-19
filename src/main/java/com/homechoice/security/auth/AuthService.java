package com.homechoice.security.auth;

import com.homechoice.model.user.User;
import com.homechoice.repository.user.UserRepository;
import com.homechoice.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for authentication-related operations such as login,
 * retrieving the authenticated user ID, and obtaining the roles of the authenticated user.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Authenticates a user with email and password, generates a JWT token if successful, and returns it in an {@link AuthResponse}.
     * <p>
     * If authentication fails or the user is not found, a {@link BadCredentialsException} is thrown.
     *
     * @param request The login request containing the user's email and password.
     * @return An {@link AuthResponse} with the JWT token.
     * @throws BadCredentialsException If authentication fails or the user does not exist.
     */
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email!"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password!");
        }

        String token = jwtService.getToken(user);

        return AuthResponse.builder()
                .token(token)
                .user(user.getFirstName())
                .build();
    }

    /**
     * Retrieves the ID of the currently authenticated user from the security context.
     *
     * @return The ID of the authenticated user.
     * @throws IllegalArgumentException if the user is not authenticated or the user type is incorrect.
     */
    public Integer getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            User user = (User) principal;
            return user.getId();
        }

        throw new IllegalArgumentException("User is not authenticated or the user type is incorrect.");
    }

    /**
     * Retrieves the roles of the currently authenticated user from the security context.
     *
     * @return A list of roles assigned to the authenticated user.
     */
    public List<String> getAuthenticatedUserRoles() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
