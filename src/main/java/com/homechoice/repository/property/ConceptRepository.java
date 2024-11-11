package com.homechoice.repository.property;

import com.homechoice.model.property.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for accessing the {@link Concept} entity in the database.
 * Provides basic CRUD operations and a custom query for finding a concept by its name.
 */
@Repository
public interface ConceptRepository extends JpaRepository<Concept, Integer> {

    /**
     * Finds a concept by its name.
     *
     * @param concept The name of the concept to search for.
     * @return An Optional containing the concept if found.
     */
    Optional<Concept> findByConcept(String concept);
}
