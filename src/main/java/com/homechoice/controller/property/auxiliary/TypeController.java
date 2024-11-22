package com.homechoice.controller.property.auxiliary;

import com.homechoice.model.property.Type;
import com.homechoice.service.property.auxiliary.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for handling API requests related to property types.
 * This class provides an endpoint to retrieve all available property types.
 *
 * @see Type
 * @see TypeService
 */
@RestController
@RequestMapping("api/properties/public/types")
@AllArgsConstructor
public class TypeController {

    private final TypeService typeService;

    /**
     * Endpoint to get all property types.
     *
     * @return a list of all available property types
     * @see Type
     */
    @GetMapping
    public List<Type> getTypes() {
        return typeService.getAll();
    }
}