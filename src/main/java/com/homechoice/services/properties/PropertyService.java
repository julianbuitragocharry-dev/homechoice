package com.homechoice.services.properties;

import com.homechoice.aws.S3Service;
import com.homechoice.dto.properties.PropertyDTO;
import com.homechoice.entities.properties.Amenity;
import com.homechoice.entities.properties.Property;
import com.homechoice.entities.properties.Image;
import com.homechoice.entities.users.User;
import com.homechoice.repositories.properties.PropertyRepository;
import com.homechoice.services.properties.auxiliaries.AmenityService;
import com.homechoice.services.properties.auxiliaries.TypeService;
import com.homechoice.services.properties.auxiliaries.ConceptService;
import com.homechoice.services.users.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PropertyService {
    private final S3Service s3Service;
    private final PropertyRepository propertyRepository;
    private final ConceptService conceptService;
    private final TypeService typeService;
    private final AmenityService amenityService;
    private final UserService userService;

    public Page<PropertyDTO> getAll(
        String name,
        Boolean status,
        BigDecimal minPrice,
        BigDecimal minArea,
        String type,
        String concept,
        Pageable pageable
    ) {
        if (name != null) {
            name = "%" + name.toLowerCase() + "%";
        }

        if(type != null) {
            type = "%" + type + "%";
        }

        if (concept != null) {
            concept = "%" + concept + "%";
        }

        return propertyRepository.findAll(
                name, status, minPrice, minArea, type, concept, pageable)
                .map(this::toDTO);
    }
    
    public List<PropertyDTO> getAllByAgentId(Integer id) {
        return propertyRepository.findByAgentId(id).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<PropertyDTO> getAllByAgentIsNull() {
        return propertyRepository.findByAgentIsNull().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public PropertyDTO getById(Integer id) {
        return toDTO(findById(id));
    }

    public PropertyDTO create(PropertyDTO dto, List<MultipartFile> images) throws IOException {
        List<String> imagePaths = s3Service.uploadFiles(images);
        dto.setImages(imagePaths);

        Property property = toEntity(dto);
        propertyRepository.save(property);
        return toDTO(property);
    }

    public void update(PropertyDTO dto, Integer id) {
        Property property = findById(id);

        property.setName(dto.getName());
        property.setArea(dto.getArea());
        property.setAddress(dto.getAddress());
        property.setLatitude(dto.getLatitude());
        property.setLongitude(dto.getLongitude());
        property.setStatus(dto.getStatus());
        property.setDescription(dto.getDescription());
        property.setConcept(conceptService.getByConcept(dto.getConcept()));
        property.setType(typeService.getByType(dto.getType()));
        property.setAmenities(amenityService.getByAmenityNames(dto.getAmenities()));

        property.setDescription(dto.getDescription());

        propertyRepository.save(property);
    }

    public void delete(Integer id) throws IOException {
        Property property = findById(id);

        List<String> paths = property.getImages().stream()
                        .map(Image::getPath)
                        .collect(Collectors.toList());

        s3Service.deleteFiles(paths);

        propertyRepository.deleteById(id);
    }

    public void setAgent(Integer id, Integer agentId) {
        Property property = findById(id);
        User agent = userService.findById(agentId);

        boolean isAgent = agent.getRoles().stream()
                .anyMatch(rol -> rol.getRol().equals("AGENT"));

        if (!isAgent) {
            throw new IllegalArgumentException("User is not an agent");
        }

        property.setAgent(agent);
        propertyRepository.save(property);
    }

    private Property findById(Integer id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property not found"));
    }

    private List<Image> getImages(Property property, List<String> paths) {
        return paths.stream().map(path -> {
                    Image image = new Image();
                    image.setPath(path);
                    image.setProperty(property);
                    return image;
                }).collect(Collectors.toList());
    }

    private Property toEntity(PropertyDTO dto) {
        Property property = Property.builder()
                .name(dto.getName())
                .area(dto.getArea())
                .price(dto.getPrice())
                .address(dto.getAddress())
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .status(dto.getStatus())
                .description(dto.getDescription())
                .agent(userService.findById(dto.getAgent()))
                .concept(conceptService.getByConcept(dto.getConcept()))
                .type(typeService.getByType(dto.getType()))
                .amenities(amenityService.getByAmenityNames(dto.getAmenities()))
                .build();

        property.setImages(getImages(property, dto.getImages()));

        return property;
    }

    private PropertyDTO toDTO(Property property) {
        return PropertyDTO.builder()
                .id(property.getId())
                .name(property.getName())
                .area(property.getArea())
                .price(property.getPrice())
                .address(property.getAddress())
                .latitude(property.getLatitude())
                .longitude(property.getLongitude())
                .status(property.getStatus())
                .description(property.getDescription())
                .agent(property.getAgent() != null ? property.getAgent().getId() : null)
                .concept(property.getConcept().getConcept())
                .type(property.getType().getType())
                .images(property.getImages().stream().map(Image::getPath).collect(Collectors.toList()))
                .amenities(property.getAmenities().stream().map(Amenity::getAmenity).collect(Collectors.toList()))
                .build();
    }
}
