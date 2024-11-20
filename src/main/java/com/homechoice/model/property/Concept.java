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
@Table(name = "type_concept")
@Schema(description = "Represents a concept type associated with a property")
public class Concept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tyc_id")
    @Schema(description = "Unique identifier for the concept type", example = "1")
    private Integer id;

    @Column(name = "tyc_name", length = 25, unique = true)
    @Schema(description = "Name of the concept", example = "Arriendo")
    private String concept;
}
