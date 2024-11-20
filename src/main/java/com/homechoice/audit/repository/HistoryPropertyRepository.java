package com.homechoice.audit.repository;

import com.homechoice.audit.model.HistoryProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryPropertyRepository extends JpaRepository<HistoryProperty, Long> {}
