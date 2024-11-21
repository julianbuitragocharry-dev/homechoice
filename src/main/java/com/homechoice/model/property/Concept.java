package com.homechoice.model.property;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity representing a concept type associated with a property.
 */
@Entity
@Data
@Table(name = "type_concept")
public class Concept {

    /**
     * Unique identifier for the concept type.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tyc_id")
    private Integer id;

    /**
     * Name of the concept.
     */
    @Column(name = "tyc_name", length = 25, unique = true)
    private String concept;
}
