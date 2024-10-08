package com.homechoice.repositories.properties;

import com.homechoice.entities.properties.TypeConcept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeConceptRepository extends JpaRepository<TypeConcept, Integer> {}
