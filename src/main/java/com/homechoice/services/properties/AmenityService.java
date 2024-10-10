package com.homechoice.services.properties;

import com.homechoice.entities.properties.Amenity;
import com.homechoice.repositories.properties.AmenityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AmenityService {
    private final AmenityRepository amenityRepository;

    public List<Amenity> getAmenitiesByIds(List<Integer> amenityIds) {
        return amenityIds.stream()
                .map(amenityId -> amenityRepository.findById(amenityId)
                        .orElseThrow(() -> new EntityNotFoundException("Amenity not found!")))
                .collect(Collectors.toList());
    }
}
