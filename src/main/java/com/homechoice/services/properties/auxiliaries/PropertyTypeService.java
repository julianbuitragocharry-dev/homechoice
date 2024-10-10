package com.homechoice.services.properties.auxiliaries;

import com.homechoice.entities.properties.PropertyType;
import com.homechoice.repositories.properties.PropertyTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PropertyTypeService {
    private final PropertyTypeRepository propertyTypeRepository;

    public List<PropertyType> getAll() {
        return propertyTypeRepository.findAll();
    }

    public PropertyType getPropertyTypeById(Integer typeId) {
        return propertyTypeRepository.findById(typeId)
                .orElseThrow(() -> new EntityNotFoundException("Type not found!"));
    }
}
