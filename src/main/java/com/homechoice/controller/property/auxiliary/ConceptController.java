package com.homechoice.controller.property.auxiliary;

import com.homechoice.model.property.Concept;
import com.homechoice.service.property.auxiliary.ConceptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/properties/public/concepts")
@AllArgsConstructor
public class ConceptController {
    private final ConceptService conceptService;

    @GetMapping
    public List<Concept> getConcepts() {
        return conceptService.getAll();
    }
}
