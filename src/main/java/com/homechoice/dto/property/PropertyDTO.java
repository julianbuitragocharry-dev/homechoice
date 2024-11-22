package com.homechoice.dto.property;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object for Property.
 * This class is used to transfer property data between processes.
 *
 * @since 1.0
 */
@Data
@Builder
public class PropertyDTO {

    /**
     * Unique identifier of the property.
     */
    private Integer id;

    /**
     * Name of the property.
     */
    private String name;

    /**
     * Area of the property in square units.
     */
    private Long area;

    /**
     * Price of the property.
     */
    private BigDecimal price;

    /**
     * Address of the property.
     */
    private String address;

    /**
     * Latitude coordinate of the property location.
     */
    private BigDecimal latitude;

    /**
     * Longitude coordinate of the property location.
     */
    private BigDecimal longitude;

    /**
     * Status indicating if the property is active or not.
     */
    private Boolean status;

    /**
     * Description of the property.
     */
    private String description;

    /**
     * Identifier of the agent responsible for the property.
     */
    private Integer agent;

    /**
     * Concept or theme of the property.
     */
    private String concept;

    /**
     * Type of the property (e.g., residential, commercial).
     */
    private String type;

    /**
     * List of image URLs associated with the property.
     */
    private List<String> images;

    /**
     * List of amenities available at the property.
     */
    private List<String> amenities;
}
