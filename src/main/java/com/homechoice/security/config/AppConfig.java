package com.homechoice.security.config;

import com.homechoice.repository.user.UserRepository;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for Spring Security.
 * It defines beans for authentication management, password encoding, and user details service.
 */
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class AppConfig {

    private final UserRepository userRepository;

    /**
     * Bean for {@link AuthenticationManager}, which is responsible for handling authentication requests.
     *
     * @param config the {@link AuthenticationConfiguration} for obtaining the authentication manager.
     * @return The {@link AuthenticationManager} instance used for authentication.
     * @throws Exception if any error occurs during the authentication manager creation.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    /**
     * Bean for {@link AuthenticationProvider}, which validates the authentication request using
     * user details and password encoding.
     *
     * @return An {@link AuthenticationProvider} configured with user details service and password encoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Bean for {@link PasswordEncoder} used to hash passwords.
     *
     * @return A {@link PasswordEncoder} instance configured with {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean for {@link UserDetailsService}, which is responsible for loading user-specific data during authentication.
     * It fetches the user details from the repository by email.
     *
     * @return A {@link UserDetailsService} that retrieves the user from the repository using the email address.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    /**
     * Bean configuration for OpenAPI.
     * This method sets up the metadata for the Swagger UI documentation.
     * It defines the title, version, description, terms of service, and support contact
     * that will be displayed in the Swagger UI interface.
     *
     * @return OpenAPI object containing the configured metadata.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HOMECHOICE API")
                        .version("1.0")
                        .description("API for property management, users, and more.")
                        .termsOfService("https://www.swagger.io/terms")
                        .contact(new Contact()
                                .name("HomeChoice Support")
                                .email("u20231211839@usco.edu.co"))
                );
    }
}
