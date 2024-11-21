package com.homechoice.audit.service;

import com.homechoice.audit.model.HistoryUser;
import com.homechoice.audit.repository.HistoryUserRepository;
import com.homechoice.dto.user.UserDTO;
import com.homechoice.model.user.User;
import com.homechoice.service.user.UserService;
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
 * Listener class for capturing and logging changes to User entities.
 * This class listens to entity lifecycle events and logs create, update, and delete actions.
 */
@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Lazy))
public class HistoryUserListener {

    private final HistoryUserRepository historyUserRepository;
    private final UserService userService;

    /**
     * Logs the creation of a User entity.
     *
     * @param entity the User entity being created
     */
    @PrePersist
    public void logCreate(User entity) {
        saveHistory(entity, "CREATE", entity);
    }

    /**
     * Logs the update of a User entity.
     *
     * @param entity the User entity being updated
     */
    @PreUpdate
    public void logUpdate(User entity) {
        saveHistory(entity, "UPDATE", entity);
    }

    /**
     * Logs the deletion of a User entity.
     *
     * @param entity the User entity being deleted
     */
    @PreRemove
    public void logDelete(User entity) {
        saveHistory(entity, "DELETE", null);
    }

    /**
     * Saves the history of changes made to a User entity.
     *
     * @param entity the User entity being changed
     * @param action the action performed (CREATE, UPDATE, DELETE)
     * @param newData the new data after the action
     */
    private void saveHistory(User entity, String action, User newData) {
        try {
            Integer entityId = entity.getId();
            String changedBy = getCurrentUser();

            HistoryUser history = HistoryUser.builder()
                    .entityId(entityId)
                    .action(action)
                    .createBy(changedBy)
                    .newData(toSimple(newData))
                    .createdAt(LocalDateTime.now())
                    .build();

            historyUserRepository.save(history);
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
     * Converts a User entity to a simple UserDTO.
     *
     * @param entity the User entity to convert
     * @return the converted UserDTO, or null if the entity is null
     */
    private UserDTO toSimple(User entity) {
        if (entity == null) {
            return null;
        }

        return userService.toDTO(entity);
    }
}
