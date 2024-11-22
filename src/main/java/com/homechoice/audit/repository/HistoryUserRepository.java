package com.homechoice.audit.repository;

import com.homechoice.audit.model.HistoryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and managing HistoryUser entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository
public interface HistoryUserRepository extends JpaRepository<HistoryUser, Long> {}
