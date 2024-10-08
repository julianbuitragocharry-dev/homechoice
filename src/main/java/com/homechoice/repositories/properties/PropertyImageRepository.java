package com.homechoice.repositories.properties;

import com.homechoice.entities.properties.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage, Integer> {}
