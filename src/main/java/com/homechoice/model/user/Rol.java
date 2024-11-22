package com.homechoice.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity representing a role in the system.
 */
@Entity
@Data
@Table(name = "rol")
public class Rol {

    /**
     * Unique identifier for the role.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    private Integer id;

    /**
     * Name of the role.
     */
    @Column(name = "rol_name", length = 25, unique = true)
    private String rol;
}
