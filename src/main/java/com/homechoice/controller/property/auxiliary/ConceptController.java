package com.homechoice.controller.property.auxiliary;

import com.homechoice.model.property.Concept;
import com.homechoice.service.property.auxiliary.ConceptService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for handling API requests related to property concepts.
 * This class provides an endpoint to retrieve all available property concepts.
 *
 * @see Concept
 * @see ConceptService
 */
@RestController
@RequestMapping("api/properties/public/concepts")
@AllArgsConstructor
public class ConceptController {

    private final ConceptService conceptService;

    /**
     * Endpoint to get all property concepts.
     *
     * @return a list of all available property concepts
     * @see Concept
     */
    @GetMapping
    public List<Concept> getConcepts() {
        return conceptService.getAll();
    }
}
