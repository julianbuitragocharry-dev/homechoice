package com.homechoice.repositories.properties;

import com.homechoice.entities.properties.Property;
import com.homechoice.entities.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
    List<Property> findByAgentIsNotNull();

    List<Property> findByAgentIsNull();

    List<Property> findByAgentId(Integer id);

    List<Property> findByAgent(User user);
}
