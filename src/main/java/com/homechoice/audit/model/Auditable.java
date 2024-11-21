package com.homechoice.audit.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Abstract base class for auditable entities. Stores the audit information
 * such as who created and last modified the entity, and when these actions occurred.
 *
 * @param <U> the type of the user identifier
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Auditable<U> {

    /**
     * The user who created the entity.
     */
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private U createdBy;

    /**
     * The date and time when the entity was created.
     */
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    /**
     * The user who last modified the entity.
     */
    @LastModifiedBy
    @Column(name = "last_modified_by")
    private U lastModifiedBy;

    /**
     * The date and time when the entity was last modified.
     */
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;
}
