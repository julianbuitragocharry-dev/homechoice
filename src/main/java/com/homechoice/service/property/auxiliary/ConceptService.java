package com.homechoice.service.property.auxiliary;

import com.homechoice.model.property.Concept;
import com.homechoice.repository.property.ConceptRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing property concepts.
 * Provides methods to retrieve all concepts or a specific concept by name.
 */
@Service
@AllArgsConstructor
public class ConceptService {

    private final ConceptRepository conceptRepository;

    /**
     * Retrieves all property concepts from the database.
     *
     * @return a list of all property concepts
     */
    public List<Concept> getAll() {
        return conceptRepository.findAll();
    }

    /**
     * Retrieves a property concept based on the concept name.
     * Throws an exception if the concept is not found.
     *
     * @param concept the name of the property concept to retrieve
     * @return the property concept matching the provided name
     * @throws EntityNotFoundException if the concept is not found
     */
    public Concept getByConcept(String concept) {
        return conceptRepository.findByConcept(concept)
                .orElseThrow(() -> new EntityNotFoundException("Concept not found"));
    }
}
