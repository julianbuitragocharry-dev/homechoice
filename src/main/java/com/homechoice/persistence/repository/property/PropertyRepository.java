package com.homechoice.persistence.repository.property;

import com.homechoice.persistence.entity.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Integer> {}
