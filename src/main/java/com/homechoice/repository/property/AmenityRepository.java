package com.homechoice.repository.property;

import com.homechoice.model.property.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for accessing the {@link Amenity} entity in the database.
 * Provides basic CRUD operations and custom queries related to property amenities.
 */
@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Integer> {

    /**
     * Finds amenities whose name is in the provided list.
     *
     * @param amenities List of amenity names to search for.
     * @return A list of amenities that match the provided names.
     */
    List<Amenity> findByAmenityIn(List<String> amenities);
}
