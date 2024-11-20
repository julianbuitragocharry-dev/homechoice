package com.homechoice.repository.user;

import com.homechoice.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for accessing the {@link User} entity in the database.
 * Provides methods for retrieving users, including filtering by email, roles, and other criteria.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to search for.
     * @return An Optional containing the user if found.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds users based on their NIT (or partial NIT) and excluding the currently authenticated user.
     *
     * @param nit The NIT of the user (optional).
     * @param authenticatedId The ID of the authenticated user to exclude.
     * @param pageable Pagination information.
     * @return A page of users that match the provided filters.
     */
    @Query("SELECT u FROM User u " +
            "WHERE (u.nit IS NULL OR u.nit ILIKE :nit)" +
            "AND u.id <> :authenticatedId")
    Page<User> findAll(
            @Param("nit") String nit,
            @Param("authenticatedId") Integer authenticatedId,
            Pageable pageable
    );

    /**
     * Finds users with a specific role and filters based on NIT, excluding the currently authenticated user.
     *
     * @param roleName The role name to filter by.
     * @param nit The NIT of the user (optional).
     * @param authenticatedId The ID of the authenticated user to exclude.
     * @param pageable Pagination information.
     * @return A page of users with the specified role that match the provided filters.
     */
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
