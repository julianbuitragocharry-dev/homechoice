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

@Component
@RequiredArgsConstructor(onConstructor_ = @__(@Lazy))
public class HistoryUserListener {

    private final HistoryUserRepository historyUserRepository;
    private final UserService userService;

    @PrePersist
    public void logCreate(User entity) {
        saveHistory(entity, "CREATE", entity);
    }

    @PreUpdate
    public void logUpdate(User entity) {
        saveHistory(entity, "UPDATE", entity);
    }

    @PreRemove
    public void logDelete(User entity) {
        saveHistory(entity, "DELETE", null);
    }

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

    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && authentication.isAuthenticated())
                ? authentication.getName()
                : "SYSTEM";
    }

    private UserDTO toSimple(User entity) {
        if (entity == null) {
            return null;
        }

        return userService.toDTO(entity);
    }
}
