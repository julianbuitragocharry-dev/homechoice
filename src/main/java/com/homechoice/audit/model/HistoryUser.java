package com.homechoice.audit.model;

import com.homechoice.audit.util.JsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity class representing a history user.
 * Stores information about changes made to a user entity.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "history_users")
public class HistoryUser {

    /**
     * Unique identifier for the history user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identifier of the user entity associated with this history user.
     */
    private Integer entityId;

    /**
     * Action performed on the user entity (e.g., create, update, delete).
     */
    private String action;

    /**
     * Date and time when the action was performed.
     */
    private LocalDateTime createdAt;

    /**
     * User who performed the action.
     */
    private String createBy;

    /**
     * New data after the action was performed, stored as JSON.
     */
    @Column(columnDefinition = "TEXT")
    @Convert(converter = JsonConverter.class)
    private Object newData;
}
