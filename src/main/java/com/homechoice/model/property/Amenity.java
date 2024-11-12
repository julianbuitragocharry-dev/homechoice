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
@Table(name = "amenity")
@Schema(description = "Represents an amenity associated with a property.")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ame_id")
    @Schema(description = "Unique identifier for the amenity", example = "1")
    private Integer id;

    @Column(name = "ame_name", length = 25, unique = true)
    @Schema(description = "Name of the amenity", example = "Piscina")
    private String amenity;
}
