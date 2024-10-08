package com.homechoice.persistence.repository.property;

import com.homechoice.persistence.entity.property.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyTypeRepository extends JpaRepository<PropertyType, Integer> {}
