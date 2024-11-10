package com.homechoice.service.property;

import com.homechoice.aws.S3Service;
import com.homechoice.dto.property.PropertyDTO;
import com.homechoice.model.property.Amenity;
import com.homechoice.model.property.Property;
import com.homechoice.model.property.Image;
import com.homechoice.model.user.User;
import com.homechoice.repository.property.PropertyRepository;
import com.homechoice.security.auth.AuthService;
import com.homechoice.service.property.auxiliary.AmenityService;
import com.homechoice.service.property.auxiliary.TypeService;
import com.homechoice.service.property.auxiliary.ConceptService;
import com.homechoice.service.user.UserService;
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
    private final AuthService authService;

    public Page<PropertyDTO> getAll(
        String name,
        Boolean status,
        BigDecimal minPrice,
        BigDecimal minArea,
        String type,
        String concept,
        Pageable pageable
    ) {
        if (name != null) { name = "%" + name + "%"; }
        if(type != null) { type = "%" + type + "%"; }
        if (concept != null) { concept = "%" + concept + "%"; }

        return propertyRepository.findAll(
                name, status, minPrice, minArea, type, concept, pageable)
                .map(this::toDTO);
    }
    
    public Page<PropertyDTO> getAllByAgentId(
            String name,
            Boolean status,
            BigDecimal minPrice,
            BigDecimal minArea,
            String type,
            String concept,
            Pageable pageable
    ) {
        if (name != null) { name = "%" + name + "%"; }
        if (type != null) { type = "%" + type + "%"; }
        if (concept != null) { concept = "%" + concept + "%"; }
        Integer id = authService.getAuthenticatedUserId();


        return propertyRepository.findByAgentId(
                name, status, minPrice, minArea, type, concept, id, pageable)
                .map(this::toDTO);
    }
    
    public Page<PropertyDTO> getAllByAgentIsNull(
            String name,
            Boolean status,
            BigDecimal minPrice,
            BigDecimal minArea,
            String type,
            String concept,
            Pageable pageable
    ) {
        if (name != null) { name = "%" + name + "%"; }
        if (type != null) { type = "%" + type + "%"; }
        if (concept != null) { concept = "%" + concept + "%"; }

        return propertyRepository.findByAgentIsNull(
                name, status, minPrice, minArea, type, concept, pageable)
                .map(this::toDTO);
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