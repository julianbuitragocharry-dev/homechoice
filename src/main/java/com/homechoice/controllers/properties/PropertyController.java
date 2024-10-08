package com.homechoice.controllers.properties;

import com.homechoice.aws.S3Service;
import com.homechoice.entities.properties.Property;
import com.homechoice.dto.properties.PropertyDTO;
import com.homechoice.services.properties.PropertyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/properties")
@AllArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;
    private final S3Service s3Service;

    @GetMapping
    public List<Property> getAll() {
        return propertyService.getAll();
    }

    @GetMapping("{id}")
    public Optional<Property> getById(@PathVariable Integer id) {
        return propertyService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Property save(
            @RequestParam("images") List<MultipartFile> images,
            @RequestPart("data") PropertyDTO propertyDTO) throws IOException {
        return propertyService.create(propertyDTO, images);
    }

    @PutMapping("{id}")
    public Property update(@PathVariable Integer id, @RequestBody Property property) {
        return propertyService.update(id, property);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Integer id) throws IOException {
        return propertyService.delete(id);
    }

    @PutMapping("{propertyId}/user/{userId}")
    public String updateUser(@PathVariable Integer propertyId, @PathVariable Integer userId) {
        return propertyService.setUserIdForProperty(propertyId, userId);
    }
}
