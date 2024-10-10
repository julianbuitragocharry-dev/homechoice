package com.homechoice.controllers.users.auxiliaries;

import com.homechoice.entities.users.Rol;
import com.homechoice.services.users.auxiliaries.RolService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users/roles")
@AllArgsConstructor
public class RolesController {
    private final RolService rolService;

    @GetMapping
    public List<Rol> getRoles() {
        return rolService.getAll();
    }
}
