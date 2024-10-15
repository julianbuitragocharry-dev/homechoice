package com.homechoice.services.properties.auxiliaries;

import com.homechoice.entities.properties.Amenity;
import com.homechoice.repositories.properties.AmenityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AmenityService {
    private final AmenityRepository amenityRepository;

    public List<Amenity> getAll() {
        return amenityRepository.findAll();
    }

    public List<Amenity> getByAmenityNames(List<String> amenities) {
        return amenityRepository.findByAmenityIn(amenities);
    }
}
