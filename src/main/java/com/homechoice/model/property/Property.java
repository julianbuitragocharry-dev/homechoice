package com.homechoice.model.property;

import com.homechoice.audit.model.Auditable;
import com.homechoice.audit.service.HistoryPropertyListener;
import com.homechoice.model.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * Entity representing a real estate property in the system.
 */
@Entity
@EntityListeners(HistoryPropertyListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "property")
public class Property extends Auditable<String> {

    /**
     * Unique identifier for the property.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    private Integer id;

    /**
     * Name of the property.
     */
    @Column(name = "pro_name", length = 100)
    private String name;

    /**
     * Area of the property in square meters.
     */
    @Column(name = "pro_area")
    private Long area;

    /**
     * Price of the property.
     */
    @Column(name = "pro_price")
    private BigDecimal price;

    /**
     * Address of the property.
     */
    @Column(name = "pro_address")
    private String address;

    /**
     * Latitude of the property location.
     */
    @Column(name = "pro_latitude", precision = 10, scale = 7)
    private BigDecimal latitude;

    /**
     * Longitude of the property location.
     */
    @Column(name = "pro_longitude", precision = 10, scale = 7)
    private BigDecimal longitude;

    /**
     * Availability status of the property.
     */
    @Column(name = "pro_status")
    private Boolean status;

    /**
     * Description of the property.
     */
    @Column(name = "pro_desp", columnDefinition = "TEXT")
    private String description;

    /**
     * Agent responsible for the property.
     */
    @ManyToOne
    @JoinColumn(name = "pro_use_id")
    private User agent;

    /**
     * Concept type of the property.
     */
    @ManyToOne
    @JoinColumn(name = "pro_tyc_id")
    private Concept concept;

    /**
     * Property type of the property.
     */
    @ManyToOne
    @JoinColumn(name = "pro_prt_id")
    private Type type;

    /**
     * List of images associated with the property.
     */
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Image> images;

    /**
     * List of amenities available in the property.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "property_amenities",
            joinColumns = @JoinColumn(name = "pra_pro_id"),
            inverseJoinColumns = @JoinColumn(name = "pra_ame_id")
    )
    private List<Amenity> amenities;
}
