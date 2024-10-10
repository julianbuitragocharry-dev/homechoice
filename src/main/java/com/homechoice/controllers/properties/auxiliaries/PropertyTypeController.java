package com.homechoice.controllers.properties.auxiliaries;

import com.homechoice.entities.properties.PropertyType;
import com.homechoice.services.properties.auxiliaries.PropertyTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("property/types")
@AllArgsConstructor
public class PropertyTypeController {
    private PropertyTypeService propertyTypeService;

    @GetMapping
    public List<PropertyType> getTypes() {
        return propertyTypeService.getAll();
    }
}
