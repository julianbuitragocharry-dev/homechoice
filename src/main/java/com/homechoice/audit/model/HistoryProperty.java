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
 * Entity class representing a history property.
 * Stores information about changes made to an entity.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "history_properties")
public class HistoryProperty {

    /**
     * Unique identifier for the history property.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Identifier of the entity associated with this history property.
     */
    private Integer entityId;

    /**
     * Action performed on the entity (e.g., create, update, delete).
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
