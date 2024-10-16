package com.homechoice.services.users.auxiliaries;

import com.homechoice.entities.users.Rol;
import com.homechoice.repositories.users.RolRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RolService {
    private final RolRepository rolRepository;

    public List<Rol> getAll() {
        return rolRepository.findAll();
    }

    public List<Rol> getByRolesNames(List<String> roles) {
        return rolRepository.findByRolIn(roles);
    }
}
