package com.homechoice.model.property;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "type_concept")
public class Concept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tyc_id")
    private Integer id;

    @Column(name = "tyc_name", length = 25, unique = true)
    private String concept;
}
