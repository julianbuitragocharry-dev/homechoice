package com.homechoice.persistence.repository.property;

import com.homechoice.persistence.entity.property.Property;
import com.homechoice.persistence.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    List<Property> findByUser(User user);
}
