package com.homechoice.service.property.auxiliary;

import com.homechoice.model.property.Amenity;
import com.homechoice.repository.property.AmenityRepository;
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
