package com.homechoice.repositories.users;

import com.homechoice.entities.users.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    List<Rol> findByRolIn(List<String> roles);
}
