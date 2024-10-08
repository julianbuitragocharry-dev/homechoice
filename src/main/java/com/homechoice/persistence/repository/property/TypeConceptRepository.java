package com.homechoice.persistence.repository.property;

import com.homechoice.persistence.entity.property.TypeConcept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeConceptRepository extends JpaRepository<TypeConcept, Integer> {}
