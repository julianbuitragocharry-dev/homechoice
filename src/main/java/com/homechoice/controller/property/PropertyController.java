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

/**
 * Controller class for managing properties.
 * This class provides endpoints for creating, updating, retrieving, and deleting properties.
 *
 * @see PropertyService
 * @see PropertyDTO
 * @see MessageResponse
 */
@RestController
@RequestMapping("api/properties")
@AllArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    /**
     * Retrieves a paginated list of public properties based on filters.
     *
     * @param name the name filter (optional)
     * @param status the status filter (optional)
     * @param minPrice the minimum price filter (optional)
     * @param minArea the minimum area filter (optional)
     * @param type the type filter (optional)
     * @param concept the concept filter (optional)
     * @param page the page number (default is 0)
     * @param size the page size (default is 6)
     * @return a paginated list of public properties
     * @see Page
     */
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

    /**
     * Retrieves properties assigned to the authenticated agent with filters.
     *
     * @param name the name filter (optional)
     * @param status the status filter (optional)
     * @param minPrice the minimum price filter (optional)
     * @param minArea the minimum area filter (optional)
     * @param type the type filter (optional)
     * @param concept the concept filter (optional)
     * @param page the page number (default is 0)
     * @param size the page size (default is 6)
     * @return a paginated list of properties assigned to the authenticated agent
     * @see Page
     */
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
            @RequestParam(defaultValue = "6") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return propertyService.getAllByAgentId(name, status, minPrice, minArea, type, concept, pageable);
    }

    /**
     * Retrieves a list of properties that are not assigned to any agent.
     *
     * @param name the name filter (optional)
     * @param status the status filter (optional)
     * @param minPrice the minimum price filter (optional)
     * @param minArea the minimum area filter (optional)
     * @param type the type filter (optional)
     * @param concept the concept filter (optional)
     * @param page the page number (default is 0)
     * @param size the page size (default is 6)
     * @return a paginated list of properties without agents
     * @see Page
     */
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

    /**
     * Retrieves a property by its ID.
     *
     * @param id the ID of the property
     * @return the property details
     * @see PropertyDTO
     */
    @GetMapping("public/{id}")
    public PropertyDTO getPropertyById(@PathVariable Integer id) {
        return propertyService.getById(id);
    }

    /**
     * Creates a new property with the given details and images.
     *
     * @param images the list of property images
     * @param request the property data
     * @return the response entity containing the created property details
     * @throws IOException if an I/O error occurs
     * @see PropertyDTO
     */
    @PreAuthorize("hasAuthority('AGENT')")
    @PostMapping
    public ResponseEntity<PropertyDTO> saveProperty(
            @RequestParam("images") List<MultipartFile> images,
            @RequestPart("data") PropertyDTO request) throws IOException {
        PropertyDTO response = propertyService.create(request, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates an existing property by ID.
     *
     * @param id the ID of the property
     * @param request the updated property data
     * @return a message response indicating the update status
     * @see MessageResponse
     */
    @PutMapping("{id}")
    public MessageResponse updateProperty(@PathVariable Integer id, @RequestBody PropertyDTO request) {
        propertyService.update(request, id);
        return new MessageResponse(
                HttpStatus.OK.value(),
                "Property updated successfully",
                LocalDateTime.now()
        );
    }

    /**
     * Freezes a property by its ID.
     * This endpoint is accessible only to users with 'SUPER_ADMIN' or 'ADMIN' authority.
     *
     * @param id the ID of the property to be frozen
     * @return a message response indicating the freeze status
     * @see MessageResponse
     */
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN', 'ADMIN')")
    @PutMapping("{id}/frozen")
    public MessageResponse freezeProperty(@PathVariable Integer id) {
        propertyService.freeze(id);
        return new MessageResponse(
                HttpStatus.OK.value(),
                "Property successfully frozen",
                LocalDateTime.now()
        );
    }

    /**
     * Deletes a property by its ID.
     *
     * @param id the ID of the property
     * @return a message response indicating the deletion status
     * @throws IOException if an I/O error occurs
     * @see MessageResponse
     */
    @DeleteMapping("{id}")
    public MessageResponse deleteProperty(@PathVariable Integer id) throws IOException {
        propertyService.delete(id);
        return new MessageResponse(
                HttpStatus.OK.value(),
                "Property deleted successfully",
                LocalDateTime.now()
        );
    }

    /**
     * Assigns an agent to a property by their IDs.
     *
     * @param id the ID of the property
     * @param agentId the ID of the agent
     * @return a message response indicating the assignment status
     * @see MessageResponse
     */
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
