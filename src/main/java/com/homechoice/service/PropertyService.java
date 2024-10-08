package com.homechoice.service;

import com.homechoice.persistence.entity.property.Property;
import com.homechoice.persistence.repository.property.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;

    public List<Property> getAll() {
        return propertyRepository.findAll();
    }

    public Optional<Property> getById(Integer id) {
        return propertyRepository.findById(id);
    }

    public Property create(Property property) {
        return propertyRepository.save(property);
    }

    public Property update(Integer id, Property property) {
        Property propertyDB = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("¡Property not found!"));

        propertyDB.setName(property.getName());
        propertyDB.setArea(property.getArea());
        propertyDB.setAddress(property.getAddress());
        propertyDB.setLatitude(property.getLatitude());
        propertyDB.setLongitude(property.getLongitude());
        propertyDB.setStatus(property.getStatus());
        propertyDB.setDescription(property.getDescription());
        propertyDB.setImages(property.getImages());

        return propertyRepository.save(propertyDB);
    }

    public String delete(Integer id) {
        propertyRepository.deleteById(id);
        return "Property deleted";
    }
}
