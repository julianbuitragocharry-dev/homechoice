package com.homechoice.services.users;

import com.homechoice.entities.users.Rol;
import com.homechoice.repositories.users.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RolService {
    private final RolRepository rolRepository;

    public List<Rol> fetchRolesByIds(List<Integer> roleIds) {
        return roleIds.stream()
                .map(roleId -> rolRepository.findById(roleId)
                        .orElseThrow(() -> new EntityNotFoundException("¡Rol not found!")))
                .collect(Collectors.toList());
    }
}
