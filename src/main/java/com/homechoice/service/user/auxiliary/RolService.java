package com.homechoice.service.user.auxiliary;

import com.homechoice.model.user.Rol;
import com.homechoice.repository.user.RolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RolService {
    private final RolRepository rolRepository;

    public List<Rol> getAll() {
        return rolRepository.findAllRoles();
    }

    public List<Rol> getByRolesNames(List<String> roles) {
        return rolRepository.findByRolIn(roles);
    }
}
