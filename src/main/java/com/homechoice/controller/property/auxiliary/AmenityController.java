package com.homechoice.controller.property.auxiliary;

import com.homechoice.model.property.Amenity;
import com.homechoice.service.property.auxiliary.AmenityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for handling API requests related to property amenities.
 * This class provides an endpoint to retrieve all available amenities.
 *
 * @see Amenity
 * @see AmenityService
 */
@RestController
@RequestMapping("api/properties/amenities")
@AllArgsConstructor
public class AmenityController {

    private final AmenityService amenityService;

    /**
     * Endpoint to get all amenities.
     *
     * @return a list of all available amenities
     * @see Amenity
     */
    @GetMapping
    public List<Amenity> getAmenities() {
        return amenityService.getAll();
    }
}
