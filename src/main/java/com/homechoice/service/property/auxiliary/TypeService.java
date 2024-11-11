package com.homechoice.service.property.auxiliary;

import com.homechoice.model.property.Type;
import com.homechoice.repository.property.TypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing property types.
 * Provides methods to retrieve all types or a specific type by name.
 */
@Service
@AllArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;

    /**
     * Retrieves all property types from the database.
     *
     * @return a list of all property types
     */
    public List<Type> getAll() {
        return typeRepository.findAll();
    }

    /**
     * Retrieves a property type based on the type name.
     * Throws an exception if the type is not found.
     *
     * @param type the name of the property type to retrieve
     * @return the property type matching the provided name
     * @throws EntityNotFoundException if the type is not found
     */
    public Type getByType(String type) {
        return typeRepository.findByType(type)
                .orElseThrow(() -> new EntityNotFoundException("Type not found"));
    }
}
