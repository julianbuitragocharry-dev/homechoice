package com.homechoice.controllers.properties.auxiliaries;

import com.homechoice.entities.properties.Amenity;
import com.homechoice.services.properties.auxiliaries.AmenityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("property/amenities")
@AllArgsConstructor
public class AmenityController {
    private final AmenityService amenityService;

    @GetMapping
    public List<Amenity> getAmenities() {
        return amenityService.getAll();
    }
}
