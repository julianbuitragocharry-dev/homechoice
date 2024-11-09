package com.homechoice.repositories.properties;

import com.homechoice.entities.properties.Property;
import com.homechoice.entities.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
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

    List<Property> findByAgent(User user);
}
