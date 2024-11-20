package com.homechoice.audit.repository;

import com.homechoice.audit.model.HistoryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryUserRepository extends JpaRepository<HistoryUser, Long> {}
