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

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Lazy))
public class HistoryPropertyListener {

    private final HistoryPropertyRepository historyPropertyRepository;
    private final PropertyService propertyService;

    @PrePersist
    public void logCreate(Property entity) {
        saveHistory(entity, "CREATE", entity);
    }

    @PreUpdate
    public void logUpdate(Property entity) {
        saveHistory(entity, "UPDATE", entity);
    }

    @PreRemove
    public void logDelete(Property entity) {
        saveHistory(entity, "DELETE", null);
    }

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

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && authentication.isAuthenticated())
                ? authentication.getName()
                : "SYSTEM";
    }

    private PropertyDTO toSimple(Property entity) {
        if (entity == null) {
            return null;
        }

        return propertyService.toDTO(entity);
    }
}
