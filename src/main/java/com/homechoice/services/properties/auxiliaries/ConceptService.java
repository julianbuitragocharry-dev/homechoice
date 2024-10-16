package com.homechoice.services.properties.auxiliaries;

import com.homechoice.entities.properties.Concept;
import com.homechoice.repositories.properties.ConceptRepository;
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
