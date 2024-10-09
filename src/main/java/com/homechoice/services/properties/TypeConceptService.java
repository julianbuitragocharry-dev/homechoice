package com.homechoice.services.properties;

import com.homechoice.entities.properties.TypeConcept;
import com.homechoice.repositories.properties.TypeConceptRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TypeConceptService {
    private final TypeConceptRepository typeConceptRepository;

    public TypeConcept getTypeConceptById(Integer conceptId) {
        return typeConceptRepository.findById(conceptId)
                .orElseThrow(() -> new EntityNotFoundException("Concept not found!"));
    }
}
