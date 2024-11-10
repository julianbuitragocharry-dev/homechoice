package com.homechoice.controller.user.auxiliary;

import com.homechoice.model.user.Rol;
import com.homechoice.service.user.auxiliary.RolService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users/roles")
@AllArgsConstructor
public class RolController {
    private final RolService rolService;

    @GetMapping
    public List<Rol> getRoles() {
        return rolService.getAll();
    }
}
