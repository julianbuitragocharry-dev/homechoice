package com.homechoice.controllers.properties.auxiliaries;

import com.homechoice.entities.properties.Concept;
import com.homechoice.services.properties.auxiliaries.ConceptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/property/concepts")
@AllArgsConstructor
public class ConceptController {
    private final ConceptService conceptService;

    @GetMapping
    public List<Concept> getConcepts() {
        return conceptService.getAll();
    }
}
