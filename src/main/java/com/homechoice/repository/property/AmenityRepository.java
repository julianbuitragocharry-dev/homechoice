package com.homechoice.repository.property;

import com.homechoice.model.property.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Integer> {
    List<Amenity> findByAmenityIn(List<String> amenities);
}
