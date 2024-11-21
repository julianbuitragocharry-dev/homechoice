package com.homechoice.controller.user.auxiliary;

import com.homechoice.model.user.Rol;
import com.homechoice.service.user.auxiliary.RolService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class for handling API requests related to user roles.
 * This class provides an endpoint to retrieve all available roles.
 *
 * @see Rol
 * @see RolService
 */
@RestController
@RequestMapping("api/users/roles")
@AllArgsConstructor
public class RolController {

    private final RolService rolService;

    /**
     * Endpoint to get all roles.
     *
     * @return a list of all available roles
     * @see Rol
     */
    @GetMapping
    public List<Rol> getRoles() {
        return rolService.getAll();
    }
}
