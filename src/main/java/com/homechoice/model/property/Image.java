package com.homechoice.model.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity representing an image associated with a property.
 */
@Entity
@Data
@Table(name = "property_image")
public class Image {

    /**
     * Unique identifier for the image.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pri_id")
    private Integer id;

    /**
     * Path or URL where the image is stored.
     */
    @Column(name = "pri_path")
    private String path;

    /**
     * The property associated with this image.
     */
    @ManyToOne
    @JoinColumn(name = "pri_pro_id")
    @JsonIgnore
    private Property property;
}
