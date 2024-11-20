package com.homechoice.repository.user;

import com.homechoice.model.user.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for accessing the {@link Rol} entity in the database.
 * Provides methods for retrieving roles and filtering based on role name.
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    /**
     * Finds all roles excluding the 'SUPER_ADMIN' role.
     *
     * @return A list of all roles except 'SUPER_ADMIN'.
     */
    @Query("SELECT r FROM Rol r WHERE r.rol <> 'SUPER_ADMIN'")
    List<Rol> findAllRoles();

    /**
     * Finds roles whose name is in the provided list.
     *
     * @param roles List of role names to search for.
     * @return A list of roles that match the provided names.
     */
    List<Rol> findByRolIn(List<String> roles);
}
