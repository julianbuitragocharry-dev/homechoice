package com.homechoice.service.property.auxiliary;

import com.homechoice.model.property.Amenity;
import com.homechoice.repository.property.AmenityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing amenities.
 * Provides methods to retrieve all amenities or a filtered list by amenity names.
 */
@Service
@AllArgsConstructor
public class AmenityService {

    private final AmenityRepository amenityRepository;

    /**
     * Retrieves all available amenities from the database.
     *
     * @return a list of all amenities
     */
    public List<Amenity> getAll() {
        return amenityRepository.findAll();
    }

    /**
     * Retrieves a list of amenities based on a list of amenity names.
     *
     * @param amenities a list of amenity names to filter by
     * @return a list of amenities matching the provided names
     */
    public List<Amenity> getByAmenityNames(List<String> amenities) {
        return amenityRepository.findByAmenityIn(amenities);
    }
}
