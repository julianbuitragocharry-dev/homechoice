package com.homechoice.services.properties;

import com.homechoice.entities.properties.Amenity;
import com.homechoice.entities.properties.Property;
import com.homechoice.entities.properties.PropertyType;
import com.homechoice.entities.properties.TypeConcept;
import com.homechoice.entities.users.User;
import com.homechoice.repositories.properties.AmenityRepository;
import com.homechoice.repositories.properties.PropertyRepository;
import com.homechoice.repositories.properties.PropertyTypeRepository;
import com.homechoice.repositories.properties.TypeConceptRepository;
import com.homechoice.repositories.users.UserRepository;
import com.homechoice.dto.properties.PropertyDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PropertyService {
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final TypeConceptRepository typeConceptRepository;
    private final PropertyTypeRepository propertyTypeRepository;
    private final AmenityRepository amenityRepository;

    public List<Property> getAll() {
        return propertyRepository.findAll();
    }

    public Optional<Property> getById(Integer id) {
        return propertyRepository.findById(id);
    }

    public Property create(PropertyDTO propertyDTO) {
        Property property = toEntity(propertyDTO);
        return propertyRepository.save(property);
    }

    public Property update(Integer id, Property property) {
        Property propertyDB = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("¡Property not found!"));

        propertyDB.setName(property.getName());
        propertyDB.setArea(property.getArea());
        propertyDB.setAddress(property.getAddress());
        propertyDB.setLatitude(property.getLatitude());
        propertyDB.setLongitude(property.getLongitude());
        propertyDB.setStatus(property.getStatus());
        propertyDB.setDescription(property.getDescription());
        propertyDB.setImages(property.getImages());

        return propertyRepository.save(propertyDB);
    }

    public String delete(Integer id) {
        propertyRepository.deleteById(id);
        return "Property deleted";
    }

    public String setUserIdForProperty(Integer propertyId, Integer userId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Property not found!"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        property.setUser(user);

        propertyRepository.save(property);
        return "A new agent will take charge of the property!";
    }

    // Aux functions

    private Property toEntity(PropertyDTO propertyDTO) {
        return Property.builder()
                .name(propertyDTO.getName())
                .area(propertyDTO.getArea())
                .price(propertyDTO.getPrice())
                .address(propertyDTO.getAddress())
                .latitude(propertyDTO.getLatitude())
                .longitude(propertyDTO.getLongitude())
                .status(propertyDTO.getStatus())
                .description(propertyDTO.getDescription())
                .user(getUserById(propertyDTO.getUser()))
                .concept(getTypeConceptById(propertyDTO.getConcept()))
                .type(getPropertyTypeById(propertyDTO.getType()))
                .amenities(getAmenitiesByIds(propertyDTO.getAmenities()))
                .build();
    }

    private User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    private TypeConcept getTypeConceptById(Integer conceptId) {
        return typeConceptRepository.findById(conceptId)
                .orElseThrow(() -> new EntityNotFoundException("Concept not found!"));
    }

    private PropertyType getPropertyTypeById(Integer typeId) {
        return propertyTypeRepository.findById(typeId)
                .orElseThrow(() -> new EntityNotFoundException("Type not found!"));
    }

    private List<Amenity> getAmenitiesByIds(List<Integer> amenityIds) {
        return amenityIds.stream()
                .map(amenityId -> amenityRepository.findById(amenityId)
                        .orElseThrow(() -> new EntityNotFoundException("Amenity not found!")))
                .collect(Collectors.toList());
    }
}
