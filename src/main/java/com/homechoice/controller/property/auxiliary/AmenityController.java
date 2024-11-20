package com.homechoice.controller.property.auxiliary;

import com.homechoice.model.property.Amenity;
import com.homechoice.service.property.auxiliary.AmenityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Property Amenities", description = "API for retrieving property amenities")
@RestController
@RequestMapping("api/properties/amenities")
@AllArgsConstructor
public class AmenityController {

    private final AmenityService amenityService;

    @GetMapping
    @Operation(summary = "Get all amenities",
            description = "Returns a list of all available amenities for properties.")
    public List<Amenity> getAmenities() {
        return amenityService.getAll();
    }
}
