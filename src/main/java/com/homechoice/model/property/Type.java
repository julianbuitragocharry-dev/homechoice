package com.homechoice.model.property;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "property_type")
@Schema(description = "Represents a type of property in the system")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prt_id")
    @Schema(description = "Unique identifier for the property type", example = "1")
    private Integer id;

    @Column(name = "prt_name", length = 25, unique = true)
    @Schema(description = "Name of the property type", example = "Tierra")
    private String type;
}
