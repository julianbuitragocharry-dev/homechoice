package com.homechoice.persistence.repository.property;

import com.homechoice.persistence.entity.property.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, Integer> {}
