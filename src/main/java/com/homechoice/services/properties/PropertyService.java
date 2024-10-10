package com.homechoice.services.properties;

import com.homechoice.aws.S3Service;
import com.homechoice.dto.properties.PropertyResponseDTO;
import com.homechoice.entities.properties.Property;
import com.homechoice.entities.properties.PropertyImage;
import com.homechoice.entities.users.User;
import com.homechoice.repositories.properties.PropertyRepository;
import com.homechoice.repositories.users.UserRepository;
import com.homechoice.dto.properties.PropertyRequestDTO;
import com.homechoice.services.properties.auxiliaries.AmenityService;
import com.homechoice.services.properties.auxiliaries.PropertyTypeService;
import com.homechoice.services.properties.auxiliaries.TypeConceptService;
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

    public List<PropertyResponseDTO> getAll() {
        return propertyRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<Property> getById(Integer id) {
        return propertyRepository.findById(id);
    }

    public Property create(PropertyRequestDTO propertyRequestDTO, List<MultipartFile> images) throws IOException {
        List<String> imagePaths = s3Service.uploadFiles(images);
        propertyRequestDTO.setImages(imagePaths);

        Property property = toEntity(propertyRequestDTO);
        return propertyRepository.save(property);
    }

    public Property update(Integer id, PropertyRequestDTO propertyRequestDTO) {
        Property propertyDB = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("¡Property not found!"));

        propertyDB.setName(propertyRequestDTO.getName());
        propertyDB.setArea(propertyRequestDTO.getArea());
        propertyDB.setPrice(propertyRequestDTO.getPrice());
        propertyDB.setAddress(propertyRequestDTO.getAddress());
        propertyDB.setLatitude(propertyRequestDTO.getLatitude());
        propertyDB.setLongitude(propertyRequestDTO.getLongitude());
        propertyDB.setStatus(propertyRequestDTO.getStatus());
        propertyDB.setDescription(propertyRequestDTO.getDescription());
        propertyDB.setConcept(typeConceptService.getTypeConceptById(propertyRequestDTO.getConcept()));
        propertyDB.setType(propertyTypeService.getPropertyTypeById(propertyRequestDTO.getType()));
        propertyDB.setAmenities(amenityService.getAmenitiesByIds(propertyRequestDTO.getAmenities()));

        return propertyRepository.save(propertyDB);
    }

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

    private Property toEntity(PropertyRequestDTO propertyRequestDTO) {
        Property property = Property.builder()
                .name(propertyRequestDTO.getName())
                .area(propertyRequestDTO.getArea())
                .price(propertyRequestDTO.getPrice())
                .address(propertyRequestDTO.getAddress())
                .latitude(propertyRequestDTO.getLatitude())
                .longitude(propertyRequestDTO.getLongitude())
                .status(propertyRequestDTO.getStatus())
                .description(propertyRequestDTO.getDescription())
                .user(userService.getUserById(propertyRequestDTO.getUser()))
                .concept(typeConceptService.getTypeConceptById(propertyRequestDTO.getConcept()))
                .type(propertyTypeService.getPropertyTypeById(propertyRequestDTO.getType()))
                .amenities(amenityService.getAmenitiesByIds(propertyRequestDTO.getAmenities()))
                .build();

        property.setImages(getImagesFromPaths(property, propertyRequestDTO.getImages()));
        return property;
    }

    private PropertyResponseDTO toDTO(Property property) {
        return PropertyResponseDTO.builder()
                .id(property.getId())
                .name(property.getName())
                .area(property.getArea())
                .price(property.getPrice())
                .status(property.getStatus())
                .concept(property.getConcept().getConcept())
                .type(property.getType().getType())
                .images(property.getImages().stream().map(PropertyImage::getPath).collect(Collectors.toList()))
                .build();
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
