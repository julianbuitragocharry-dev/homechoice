package com.homechoice.repositories.properties;

import com.homechoice.entities.properties.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Integer> {
    List<Amenity> findByAmenityIn(List<String> amenities);
}
