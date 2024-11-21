package com.homechoice.audit.repository;

import com.homechoice.audit.model.HistoryProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing HistoryProperty entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface HistoryPropertyRepository extends JpaRepository<HistoryProperty, Long> {}
