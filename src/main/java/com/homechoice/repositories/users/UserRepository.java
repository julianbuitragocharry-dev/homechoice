package com.homechoice.repositories.users;

import com.homechoice.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u " +
            "WHERE (u.nit IS NULL OR u.nit ILIKE :nit)" +
            "AND u.id <> :authenticatedId")
    Page<User> findAll(
            @Param("nit") String nit,
            @Param("authenticatedId") Integer authenticatedId,
            Pageable pageable
    );

    @Query("SELECT u FROM User u " +
            "JOIN u.roles r " +
            "WHERE (r.rol = :roleName)" +
            "AND (u.nit IS NULL OR u.nit ILIKE :nit)" +
            "AND u.id <> :authenticatedId")
    Page<User> findByRolesRol(
            String roleName,
            @Param("nit") String nit,
            @Param("authenticatedId") Integer authenticatedId,
            Pageable pageable
    );
}
