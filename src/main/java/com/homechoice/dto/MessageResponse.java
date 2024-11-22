package com.homechoice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response object for status messages, including status code, message, and timestamp.
 * This class is used to encapsulate the response details.
 *
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    /**
     * Status code of the response.
     * Example: 200
     */
    private Integer status;

    /**
     * Detailed message about the response.
     * Example: Operation completed successfully.
     */
    private String message;

    /**
     * Timestamp when the response was generated.
     * Example: 2024-11-11T12:00:00
     */
    private LocalDateTime timestamp;
}
