package com.homechoice.presentation.controller;

import com.homechoice.persistence.entity.property.Property;
import com.homechoice.service.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/properties")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping
    public List<Property> getAll() {
        return propertyService.getAll();
    }

    @GetMapping("{id}")
    public Optional<Property> getById(@PathVariable Integer id) {
        return propertyService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Property save(@RequestBody Property property) {
        return propertyService.create(property);
    }

    @PutMapping("{id}")
    public Property update(@PathVariable Integer id, @RequestBody Property property) {
        return propertyService.update(id, property);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id) {
        return propertyService.delete(id);
    }
}
