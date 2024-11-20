package com.homechoice.service.user.auxiliary;

import com.homechoice.model.user.Rol;
import com.homechoice.repository.user.RolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing roles.
 * Provides methods to retrieve all roles and find roles by a list of role names.
 */
@Service
@AllArgsConstructor
public class RolService {

    private final RolRepository rolRepository;

    /**
     * Retrieves a list of all roles.
     *
     * @return a list of all roles in the database
     */
    public List<Rol> getAll() {
        return rolRepository.findAllRoles();
    }

    /**
     * Finds roles by their names.
     *
     * @param roles a list of role names to search for
     * @return a list of roles matching the specified names
     */
    public List<Rol> getByRolesNames(List<String> roles) {
        return rolRepository.findByRolIn(roles);
    }
}
