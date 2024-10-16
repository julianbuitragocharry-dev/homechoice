package com.homechoice.entities.properties;

import com.homechoice.entities.users.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    private Integer id;

    @Column(name = "pro_name", length = 100)
    private String name;

    @Column(name = "pro_area")
    private Long area;

    @Column(name = "pro_price")
    private BigDecimal price;

    @Column(name = "pro_address")
    private String address;

    @Column(name = "pro_latitude", precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "pro_longitude", precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "pro_status")
    private Boolean status;

    @Column(name = "pro_desp", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "pro_use_id")
    private User agent;

    @ManyToOne
    @JoinColumn(name = "pro_tyc_id")
    private Concept concept;

    @ManyToOne
    @JoinColumn(name = "pro_prt_id")
    private Type type;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Image> images;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "property_amenities",
            joinColumns = @JoinColumn(name = "pra_pro_id"),
            inverseJoinColumns = @JoinColumn(name = "pra_ame_id")
    )
    private List<Amenity> amenities;
}
