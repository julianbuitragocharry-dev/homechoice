package com.homechoice.controller.user.auxiliary;

import com.homechoice.model.user.Rol;
import com.homechoice.service.user.auxiliary.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Roles", description = "API for retrieving user roles")
@RestController
@RequestMapping("api/users/roles")
@AllArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    @Operation(summary = "Get all roles",
            description = "Returns a list of all available roles.")
    public List<Rol> getRoles() {
        return rolService.getAll();
    }
}
