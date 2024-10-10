package com.homechoice.controllers.properties.auxiliaries;

import com.homechoice.entities.properties.TypeConcept;
import com.homechoice.services.properties.auxiliaries.TypeConceptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("property/concepts")
@AllArgsConstructor
public class TypeConceptController {
    private TypeConceptService typeConceptService;

    @GetMapping
    public List<TypeConcept> getConcepts() {
        return typeConceptService.getAll();
    }
}
