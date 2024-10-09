package com.homechoice.services.properties;

import com.homechoice.aws.S3Service;
import com.homechoice.entities.properties.Property;
import com.homechoice.entities.properties.PropertyImage;
import com.homechoice.entities.users.User;
import com.homechoice.repositories.properties.PropertyRepository;
import com.homechoice.repositories.users.UserRepository;
import com.homechoice.dto.properties.PropertyDTO;
import com.homechoice.services.users.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PropertyService {
    private final S3Service s3Service;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final TypeConceptService typeConceptService;
    private final AmenityService amenityService;
    private final UserService userService;
    private final PropertyTypeService propertyTypeService;

    public List<Property> getAll() {
        return propertyRepository.findAll();
    }

    public Optional<Property> getById(Integer id) {
        return propertyRepository.findById(id);
    }

    public Property create(PropertyDTO propertyDTO, List<MultipartFile> images) throws IOException {
        List<String> imagePaths = s3Service.uploadFiles(images);
        propertyDTO.setImages(imagePaths);

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

    // Function with bug
    public String delete(Integer id) throws IOException {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property not found!"));

        List<String> paths = property.getImages().stream()
                .map(PropertyImage::getPath)
                .collect(Collectors.toList());

        s3Service.deleteFiles(paths);

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

    private Property toEntity(PropertyDTO propertyDTO) {
        Property property = Property.builder()
                .name(propertyDTO.getName())
                .area(propertyDTO.getArea())
                .price(propertyDTO.getPrice())
                .address(propertyDTO.getAddress())
                .latitude(propertyDTO.getLatitude())
                .longitude(propertyDTO.getLongitude())
                .status(propertyDTO.getStatus())
                .description(propertyDTO.getDescription())
                .user(userService.getUserById(propertyDTO.getUser()))
                .concept(typeConceptService.getTypeConceptById(propertyDTO.getConcept()))
                .type(propertyTypeService.getPropertyTypeById(propertyDTO.getType()))
                .amenities(amenityService.getAmenitiesByIds(propertyDTO.getAmenities()))
                .build();

        property.setImages(getImagesFromPaths(property, propertyDTO.getImages()));
        return property;
    }

    private List<PropertyImage> getImagesFromPaths(Property property, List<String> images) {
        return images.stream()
                .map(path -> {
                    PropertyImage propertyImage = new PropertyImage();
                    propertyImage.setPath(path);
                    propertyImage.setProperty(property);
                    return propertyImage;
                })
                .collect(Collectors.toList());
    }
}
