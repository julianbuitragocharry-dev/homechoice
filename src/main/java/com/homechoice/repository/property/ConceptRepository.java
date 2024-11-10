package com.homechoice.repository.property;

import com.homechoice.model.property.Concept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConceptRepository extends JpaRepository<Concept, Integer> {
    Optional<Concept> findByConcept(String concept);
}
