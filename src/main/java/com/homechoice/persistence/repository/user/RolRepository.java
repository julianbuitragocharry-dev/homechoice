package com.homechoice.persistence.repository.user;

import com.homechoice.persistence.entity.user.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {}
