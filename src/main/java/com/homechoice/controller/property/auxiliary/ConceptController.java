package com.homechoice.controller.property.auxiliary;

import com.homechoice.model.property.Concept;
import com.homechoice.service.property.auxiliary.ConceptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Property Concepts", description = "API for retrieving property concepts")
@RestController
@RequestMapping("api/properties/public/concepts")
@AllArgsConstructor
public class ConceptController {

    private final ConceptService conceptService;

    @GetMapping
    @Operation(summary = "Get all property concepts",
            description = "Returns a list of all available property concepts.")
    public List<Concept> getConcepts() {
        return conceptService.getAll();
    }
}
