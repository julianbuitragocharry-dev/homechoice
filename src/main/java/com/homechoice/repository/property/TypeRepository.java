package com.homechoice.repository.property;

import com.homechoice.model.property.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for accessing the {@link Type} entity in the database.
 * Provides basic CRUD operations and a custom query for finding a property type by its name.
 */
@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

    /**
     * Finds a property type by its name.
     *
     * @param type The name of the property type to search for.
     * @return An Optional containing the type if found.
     */
    Optional<Type> findByType(String type);
}
