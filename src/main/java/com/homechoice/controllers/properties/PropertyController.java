package com.homechoice.controllers.properties;

import com.homechoice.dto.properties.PropertyDTO;
import com.homechoice.services.properties.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.List;

@RestController
@RequestMapping("/properties")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping
    public List<PropertyDTO> getProperties() {
        return propertyService.getAll();
    }

    @GetMapping("agent/{id}")
    public List<PropertyDTO> getPropertiesByAgentId(@PathVariable Integer id) {
        return propertyService.getAllByAgentId(id);
    }

    @GetMapping("null")
    public List<PropertyDTO> getPropertiesAgentIsNull() {
        return propertyService.getAllByAgentIsNull();
    }

    @GetMapping("{id}")
    public PropertyDTO getPropertyById(@PathVariable Integer id) {
        return propertyService.getById(id);
    }

    @PostMapping
    public ResponseEntity<PropertyDTO> saveProperty(
            @RequestParam("images") List<MultipartFile> images,
            @RequestPart("data") PropertyDTO request) throws IOException {
        PropertyDTO dto = propertyService.create(request, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateProperty(@PathVariable Integer id, @RequestBody PropertyDTO request) {
        propertyService.update(request, id);
        return ResponseEntity.ok("Property updated");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProperty(@PathVariable Integer id) throws IOException {
        propertyService.delete(id);
        return ResponseEntity.ok("Property deleted");
    }

    @PutMapping("{id}/user/{agentId}")
    public ResponseEntity<String> updateUserProperty(@PathVariable Integer id, @PathVariable Integer agentId) {
        propertyService.setAgent(id, agentId);
        return ResponseEntity.ok("Agent " + agentId + " has been assigned to this property");
    }
}
