package com.homechoice.service.property.auxiliary;

import com.homechoice.model.property.Concept;
import com.homechoice.repository.property.ConceptRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ConceptService {
    private final ConceptRepository conceptRepository;

    public List<Concept> getAll() {
        return conceptRepository.findAll();
    }

    public Concept getByConcept(String concept) {
        return conceptRepository.findByConcept(concept)
                .orElseThrow(() -> new EntityNotFoundException("Concept not found"));
    }
}
