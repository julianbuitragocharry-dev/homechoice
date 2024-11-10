package com.homechoice.repository.user;

import com.homechoice.model.user.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    @Query("SELECT r FROM Rol r WHERE r.rol <> 'SUPER_ADMIN'")
    List<Rol> findAllRoles();

    List<Rol> findByRolIn(List<String> roles);
}
