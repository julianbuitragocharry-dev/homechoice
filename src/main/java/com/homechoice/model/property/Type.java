package com.homechoice.model.property;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity representing a type of property in the system.
 */
@Entity
@Data
@Table(name = "property_type")
public class Type {

    /**
     * Unique identifier for the property type.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prt_id")
    private Integer id;

    /**
     * Name of the property type.
     */
    @Column(name = "prt_name", length = 25, unique = true)
    private String type;
}
