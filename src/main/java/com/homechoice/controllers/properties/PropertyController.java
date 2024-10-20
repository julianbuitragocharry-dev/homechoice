package com.homechoice.controllers.properties;

import com.homechoice.dto.properties.PropertyDTO;
import com.homechoice.responses.ApiResponse;
import com.homechoice.services.properties.PropertyService;
import lombok.AllArgsConstructor;
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
import java.util.List;

@RestController
@RequestMapping("api/properties")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping("/public")
    public List<PropertyDTO> getProperties(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal minArea,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String concept
            ) {
        return propertyService.getAll(
                name, status, minPrice, minArea, type, concept
        );
    }

    @PreAuthorize("hasAuthority('AGENT')")
    @GetMapping("agent/{id}")
    public List<PropertyDTO> getPropertiesByAgentId(@PathVariable Integer id) {
        return propertyService.getAllByAgentId(id);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    @GetMapping("nulls")
    public List<PropertyDTO> getPropertiesAgentIsNull() {
        return propertyService.getAllByAgentIsNull();
    }

    @GetMapping("{id}")
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
    public ResponseEntity<ApiResponse> updateProperty(@PathVariable Integer id, @RequestBody PropertyDTO request) {
        propertyService.update(request, id);
        return ResponseEntity.ok(new ApiResponse("Property updated"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse> deleteProperty(@PathVariable Integer id) throws IOException {
        propertyService.delete(id);
        return ResponseEntity.ok(new ApiResponse("Property deleted"));
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    @PutMapping("{id}/user/{agentId}")
    public ResponseEntity<ApiResponse> updateUserProperty(@PathVariable Integer id, @PathVariable Integer agentId) {
        propertyService.setAgent(id, agentId);
        return ResponseEntity.ok(new ApiResponse("Agent " + agentId + " has been assigned to this property"));
    }
}
