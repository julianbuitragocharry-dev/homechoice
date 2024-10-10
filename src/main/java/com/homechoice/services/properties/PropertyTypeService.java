package com.homechoice.services.properties;

import com.homechoice.entities.properties.PropertyType;
import com.homechoice.repositories.properties.PropertyTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropertyTypeService {
    private final PropertyTypeRepository propertyTypeRepository;

    public PropertyType getPropertyTypeById(Integer typeId) {
        return propertyTypeRepository.findById(typeId)
                .orElseThrow(() -> new EntityNotFoundException("Type not found!"));
    }
}
