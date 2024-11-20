package com.homechoice.repository.property;

import com.homechoice.model.property.Property;
import com.homechoice.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Repository for accessing the {@link Property} entity in the database.
 * Provides methods for retrieving properties with custom filtering and pagination based on various criteria such as name, status, price, area, type, concept, and agent.
 */
@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    /**
     * Finds all properties based on the provided filters and pagination.
     *
     * @param name Name of the property (optional).
     * @param status Status of the property (optional).
     * @param minPrice Minimum price of the property (optional).
     * @param minArea Minimum area of the property (optional).
     * @param type Type of the property (optional).
     * @param concept Concept of the property (optional).
     * @param pageable Pagination information.
     * @return A page of properties that match the provided filters.
     */
    @Query("SELECT p FROM Property p " +
            "JOIN p.type t " +
            "JOIN p.concept c " +
            "WHERE (:name IS NULL OR p.name ILIKE :name)" +
            "AND (:status IS NULL OR p.status = :status)" +
            "AND (:minPrice IS NULL OR p.price >= :minPrice)" +
            "AND (:minArea IS NULL OR p.area >= :minArea)" +
            "AND (:type IS NULL OR t.type ILIKE :type)" +
            "AND (:concept IS NULL OR c.concept ILIKE :concept)" +
            "AND (p.agent IS NOT NULL)")
    Page<Property> findAll(
            @Param("name") String name,
            @Param("status") Boolean status,
            @Param("minPrice") BigDecimal minPrice,
            @Param("minArea") BigDecimal minArea,
            @Param("type") String type,
            @Param("concept") String concept,
            Pageable pageable);

    /**
     * Finds properties where the agent is null based on the provided filters and pagination.
     *
     * @param name Name of the property (optional).
     * @param status Status of the property (optional).
     * @param minPrice Minimum price of the property (optional).
     * @param minArea Minimum area of the property (optional).
     * @param type Type of the property (optional).
     * @param concept Concept of the property (optional).
     * @param pageable Pagination information.
     * @return A page of properties with no assigned agent that match the provided filters.
     */
    @Query("SELECT p FROM Property p " +
            "JOIN p.type t " +
            "JOIN p.concept c " +
            "WHERE (:name IS NULL OR p.name ILIKE :name) " +
            "AND (:status IS NULL OR p.status = :status) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:minArea IS NULL OR p.area >= :minArea) " +
            "AND (:type IS NULL OR t.type ILIKE :type) " +
            "AND (:concept IS NULL OR c.concept ILIKE :concept) " +
            "AND p.agent IS NULL")
    Page<Property> findByAgentIsNull(
            @Param("name") String name,
            @Param("status") Boolean status,
            @Param("minPrice") BigDecimal minPrice,
            @Param("minArea") BigDecimal minArea,
            @Param("type") String type,
            @Param("concept") String concept,
            Pageable pageable);

    /**
     * Finds properties assigned to a specific agent based on the provided filters and pagination.
     *
     * @param name Name of the property (optional).
     * @param status Status of the property (optional).
     * @param minPrice Minimum price of the property (optional).
     * @param minArea Minimum area of the property (optional).
     * @param type Type of the property (optional).
     * @param concept Concept of the property (optional).
     * @param agentId The ID of the agent whose properties to retrieve.
     * @param pageable Pagination information.
     * @return A page of properties assigned to the specified agent that match the provided filters.
     */
    @Query("SELECT p FROM Property p " +
            "JOIN p.type t " +
            "JOIN p.concept c " +
            "WHERE (:name IS NULL OR p.name ILIKE :name) " +
            "AND (:status IS NULL OR p.status = :status) " +
            "AND (:minPrice IS NULL OR p.price >= :minPrice) " +
            "AND (:minArea IS NULL OR p.area >= :minArea) " +
            "AND (:type IS NULL OR t.type ILIKE :type) " +
            "AND (:concept IS NULL OR c.concept ILIKE :concept) " +
            "AND p.agent.id = :agentId")
    Page<Property> findByAgentId(
            @Param("name") String name,
            @Param("status") Boolean status,
            @Param("minPrice") BigDecimal minPrice,
            @Param("minArea") BigDecimal minArea,
            @Param("type") String type,
            @Param("concept") String concept,
            @Param("agentId") Integer agentId,
            Pageable pageable
    );

    /**
     * Finds all properties assigned to a specific agent.
     *
     * @param user The agent whose properties to retrieve.
     * @return A list of properties assigned to the specified agent.
     */
    List<Property> findByAgent(User user);
}
