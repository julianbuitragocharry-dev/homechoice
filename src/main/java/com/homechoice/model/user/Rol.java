package com.homechoice.model.user;

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
@Table(name = "rol")
@Schema(description = "Represents a role in the system")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the role", example = "1")
    @Column(name = "rol_id")
    private Integer id;

    @Column(name = "rol_name", length = 25, unique = true)
    @Schema(description = "Name of the role", example = "ADMIN")
    private String rol;
}
