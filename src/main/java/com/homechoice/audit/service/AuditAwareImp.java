package com.homechoice.audit.service;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Implementation of AuditorAware interface to provide the current auditor.
 * This class retrieves the current authenticated user from the SecurityContext.
 */
public class AuditAwareImp implements AuditorAware<String> {

    /**
     * Returns the current auditor based on the SecurityContext.
     *
     * @return an Optional containing the username of the current authenticated user, or an empty Optional if no user is authenticated.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getName);
    }
}
