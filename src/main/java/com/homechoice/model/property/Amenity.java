package com.homechoice.model.property;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity representing an amenity associated with a property.
 */
@Entity
@Data
@Table(name = "amenity")
public class Amenity {

    /**
     * Unique identifier for the amenity.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ame_id")
    private Integer id;

    /**
     * Name of the amenity.
     */
    @Column(name = "ame_name", length = 25, unique = true)
    private String amenity;
}
