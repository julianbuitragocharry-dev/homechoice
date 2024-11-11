package com.homechoice.controller.property;

import com.homechoice.dto.property.PropertyDTO;
import com.homechoice.dto.MessageResponse;
import com.homechoice.service.property.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/properties")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping("/public")
    public Page<?> getProperties(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal minArea,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String concept,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "6") Integer size
            ) {
        Pageable pageable = PageRequest.of(page, size);
        return propertyService.getAll(name, status, minPrice, minArea, type, concept, pageable);
    }

    @PreAuthorize("hasAuthority('AGENT')")
    @GetMapping("agent")
    public Page<?> getPropertiesByAgentId(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal minArea,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String concept,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "6") Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return propertyService.getAllByAgentId(name, status, minPrice, minArea, type, concept, pageable);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("nulls")
    public Page<?> getPropertiesAgentIsNull(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal minArea,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String concept,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "6") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return propertyService.getAllByAgentIsNull(name, status, minPrice, minArea, type, concept, pageable);
    }

    @GetMapping("public/{id}")
    public PropertyDTO getPropertyById(@PathVariable Integer id) {
        return propertyService.getById(id);
    }

    @PreAuthorize("hasAuthority('AGENT')")
    @PostMapping
    public ResponseEntity<PropertyDTO> saveProperty(
            @RequestParam("images") List<MultipartFile> images,
            @RequestPart("data") PropertyDTO request) throws IOException {
        PropertyDTO response = propertyService.create(request, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    public MessageResponse updateProperty(@PathVariable Integer id, @RequestBody PropertyDTO request) {
        propertyService.update(request, id);
        return new MessageResponse(
                HttpStatus.OK.value(),
                "Property updated successfully",
                LocalDateTime.now()
        );
    }

    @DeleteMapping("{id}")
    public MessageResponse deleteProperty(@PathVariable Integer id) throws IOException {
        propertyService.delete(id);
        return new MessageResponse(
                HttpStatus.OK.value(),
                "Property deleted successfully",
                LocalDateTime.now()
        );
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    @PutMapping("{id}/user/{agentId}")
    public MessageResponse updateUserProperty(@PathVariable Integer id, @PathVariable Integer agentId) {
        propertyService.setAgent(id, agentId);
        return new MessageResponse(
                HttpStatus.OK.value(),
                "Agent" + agentId + " has been assigned to this property",
                LocalDateTime.now()
        );
    }
}
