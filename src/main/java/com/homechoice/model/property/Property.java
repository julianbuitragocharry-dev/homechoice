package com.homechoice.model.property;

import com.homechoice.audit.model.Auditable;
import com.homechoice.audit.service.HistoryPropertyListener;
import com.homechoice.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
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

@Entity
@EntityListeners(HistoryPropertyListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "property")
@Schema(description = "Represents a real estate property in the system.")
public class Property extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pro_id")
    @Schema(description = "Unique identifier for the property", example = "1")
    private Integer id;

    @Column(name = "pro_name", length = 100)
    @Schema(description = "Name of the property", example = "Lujoso Apartamento")
    private String name;

    @Column(name = "pro_area")
    @Schema(description = "Area of the property in square meters", example = "120")
    private Long area;

    @Column(name = "pro_price")
    @Schema(description = "Price of the property", example = "250000000.00")
    private BigDecimal price;

    @Column(name = "pro_address")
    @Schema(description = "Address of the property", example = "Cll 59 #25-32, Neiva")
    private String address;

    @Column(name = "pro_latitude", precision = 10, scale = 7)
    @Schema(description = "Latitude of the property location", example = "37.7749295")
    private BigDecimal latitude;

    @Column(name = "pro_longitude", precision = 10, scale = 7)
    @Schema(description = "Longitude of the property location", example = "-122.4194155")
    private BigDecimal longitude;

    @Column(name = "pro_status")
    @Schema(description = "Availability status of the property", example = "true")
    private Boolean status;

    @Column(name = "pro_desp", columnDefinition = "TEXT")
    @Schema(description = "Description of the property", example = "Este lujoso apartamento ofrece un diseño moderno y sofisticado, combinado con amplios espacios que proporcionan comodidad y elegancia. Ubicado en una de las zonas más exclusivas, cuenta con vistas panorámicas impresionantes, ideales para quienes buscan lujo y privacidad. La cocina gourmet, los acabados de alta calidad y las instalaciones de última tecnología hacen de este apartamento una opción perfecta para quienes valoran lo mejor. Con su ambiente acogedor y su atención al detalle, este apartamento redefine lo que significa vivir con estilo.")
    private String description;

    @ManyToOne
    @JoinColumn(name = "pro_use_id")
    @Schema(description = "Agent responsible for the property")
    private User agent;

    @ManyToOne
    @JoinColumn(name = "pro_tyc_id")
    @Schema(description = "Concept type of the property")
    private Concept concept;

    @ManyToOne
    @JoinColumn(name = "pro_prt_id")
    @Schema(description = "Property type of the property")
    private Type type;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    @Schema(description = "List of images associated with the property")
    private List<Image> images;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "property_amenities",
            joinColumns = @JoinColumn(name = "pra_pro_id"),
            inverseJoinColumns = @JoinColumn(name = "pra_ame_id")
    )
    @Schema(description = "List of amenities available in the property")
    private List<Amenity> amenities;
}
