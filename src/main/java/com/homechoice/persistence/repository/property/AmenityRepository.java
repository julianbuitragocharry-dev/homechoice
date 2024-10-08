package com.homechoice.persistence.repository.property;

import com.homechoice.persistence.entity.property.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Integer> {}
