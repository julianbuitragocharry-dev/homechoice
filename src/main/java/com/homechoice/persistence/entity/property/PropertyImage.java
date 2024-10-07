package com.homechoice.persistence.entity.property;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "property_image")
public class PropertyImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pri_id")
    private Integer id;

    @Column(name = "pri_path")
    private String path;

    @Column(name = "pri_is_thumb")
    private Boolean isThumb;

    @ManyToOne
    @JoinColumn(name = "pri_pro_id")
    private Property property;
}
