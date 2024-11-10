package com.homechoice.controller.properties.auxiliary;

import com.homechoice.model.property.Type;
import com.homechoice.service.property.auxiliary.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/properties/public/types")
@AllArgsConstructor
public class TypeController {
    private final TypeService typeService;

    @GetMapping
    public List<Type> getTypes() {
        return typeService.getAll();
    }
}
