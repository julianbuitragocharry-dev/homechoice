package com.homechoice.model.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "property_image")
@Schema(description = "Represents an image associated with a property.")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pri_id")
    @Schema(description = "Unique identifier for the image", example = "1")
    private Integer id;

    @Column(name = "pri_path")
    @Schema(description = "Path or URL where the image is stored", example = "https://amazonaws.com/bucket-name/property123.jpg")
    private String path;

    @ManyToOne
    @JoinColumn(name = "pri_pro_id")
    @Schema(description = "The property associated with this image")
    @JsonIgnore
    private Property property;
}
