package com.homechoice.repositories.properties;

import com.homechoice.entities.properties.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConceptRepository extends JpaRepository<Concept, Integer> {
    Optional<Concept> findByConcept(String concept);
}
