package com.homechoice.service;

import com.homechoice.persistence.entity.property.Amenity;
import com.homechoice.persistence.entity.property.Property;
import com.homechoice.persistence.entity.property.PropertyType;
import com.homechoice.persistence.entity.property.TypeConcept;
import com.homechoice.persistence.entity.user.User;
import com.homechoice.persistence.repository.property.AmenityRepository;
import com.homechoice.persistence.repository.property.PropertyRepository;
import com.homechoice.persistence.repository.property.PropertyTypeRepository;
import com.homechoice.persistence.repository.property.TypeConceptRepository;
import com.homechoice.persistence.repository.user.UserRepository;
import com.homechoice.presentation.dto.PropertyDTO;
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
