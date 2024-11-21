package com.homechoice.audit.service;

import com.homechoice.audit.model.HistoryProperty;
import com.homechoice.audit.repository.HistoryPropertyRepository;
import com.homechoice.dto.property.PropertyDTO;
import com.homechoice.model.property.Property;
import com.homechoice.service.property.PropertyService;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Listener class for capturing and logging changes to Property entities.
 * This class listens to entity lifecycle events and logs create, update, and delete actions.
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Lazy))
public class HistoryPropertyListener {

    private final HistoryPropertyRepository historyPropertyRepository;
    private final PropertyService propertyService;

    /**
     * Logs the creation of a Property entity.
     *
     * @param entity the Property entity being created
     */
    @PrePersist
    public void logCreate(Property entity) {
        saveHistory(entity, "CREATE", entity);
    }

    /**
     * Logs the update of a Property entity.
     *
     * @param entity the Property entity being updated
     */
    @PreUpdate
    public void logUpdate(Property entity) {
        saveHistory(entity, "UPDATE", entity);
    }

    /**
     * Logs the deletion of a Property entity.
     *
     * @param entity the Property entity being deleted
     */
    @PreRemove
    public void logDelete(Property entity) {
        saveHistory(entity, "DELETE", null);
    }

    /**
     * Saves the history of changes made to a Property entity.
     *
     * @param entity the Property entity being changed
     * @param action the action performed (CREATE, UPDATE, DELETE)
     * @param newData the new data after the action
     */
    private void saveHistory(Property entity, String action, Property newData) {
        try {
            Integer entityId = entity.getId();
            String changedBy = getCurrentUser();

            HistoryProperty history = HistoryProperty.builder()
                    .entityId(entityId)
                    .action(action)
                    .createBy(changedBy)
                    .newData(toSimple(newData))
                    .createdAt(LocalDateTime.now())
                    .build();

            historyPropertyRepository.save(history);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Retrieves the current authenticated user.
     *
     * @return the username of the current authenticated user, or "SYSTEM" if no user is authenticated
     */
    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && authentication.isAuthenticated())
                ? authentication.getName()
                : "SYSTEM";
    }

    /**
     * Converts a Property entity to a simple PropertyDTO.
     *
     * @param entity the Property entity to convert
     * @return the converted PropertyDTO, or null if the entity is null
     */
    private PropertyDTO toSimple(Property entity) {
        if (entity == null) {
            return null;
        }

        return propertyService.toDTO(entity);
    }
}
