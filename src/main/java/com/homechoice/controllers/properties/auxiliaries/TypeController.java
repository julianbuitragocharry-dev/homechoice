package com.homechoice.controllers.properties.auxiliaries;

import com.homechoice.entities.properties.Type;
import com.homechoice.services.properties.auxiliaries.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/property/types")
@AllArgsConstructor
public class TypeController {
    private final TypeService typeService;

    @GetMapping
    public List<Type> getTypes() {
        return typeService.getAll();
    }
}
