package com.homechoice.persistence.repository.property;

import com.homechoice.persistence.entity.property.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Integer> {}
