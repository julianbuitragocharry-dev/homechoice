package com.homechoice.services.properties.auxiliaries;

import com.homechoice.entities.properties.Type;
import com.homechoice.repositories.properties.TypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TypeService {
    private final TypeRepository typeRepository;

    public List<Type> getAll() {
        return typeRepository.findAll();
    }

    public Type getByType(String type) {
        return typeRepository.findByType(type)
                .orElseThrow(() -> new EntityNotFoundException("Type not found"));
    }
}
