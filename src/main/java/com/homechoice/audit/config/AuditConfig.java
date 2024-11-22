package com.homechoice.audit.config;

import com.homechoice.audit.service.AuditAwareImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class for enabling JPA auditing.
 * Provides a bean for the AuditorAware implementation.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {

    /**
     * Bean definition for AuditorAware implementation.
     * This bean is used to provide the current auditor (user) for auditing purposes.
     *
     * @return an instance of AuditAwareImp
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditAwareImp();
    }
}
