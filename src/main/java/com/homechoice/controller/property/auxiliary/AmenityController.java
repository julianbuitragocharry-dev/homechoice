package com.homechoice.controller.property.auxiliary;

import com.homechoice.model.property.Amenity;
import com.homechoice.service.property.auxiliary.AmenityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/properties/amenities")
@AllArgsConstructor
public class AmenityController {
    private final AmenityService amenityService;

    @GetMapping
    public List<Amenity> getAmenities() {
        return amenityService.getAll();
    }
}
