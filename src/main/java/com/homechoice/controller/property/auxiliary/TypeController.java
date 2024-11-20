package com.homechoice.controller.property.auxiliary;

import com.homechoice.model.property.Type;
import com.homechoice.service.property.auxiliary.TypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Property Types", description = "API for retrieving property types")
@RestController
@RequestMapping("api/properties/public/types")
@AllArgsConstructor
public class TypeController {

    private final TypeService typeService;

    @GetMapping
    @Operation(summary = "Get all property types",
            description = "Returns a list of all available property types.")
    public List<Type> getTypes() {
        return typeService.getAll();
    }
}
