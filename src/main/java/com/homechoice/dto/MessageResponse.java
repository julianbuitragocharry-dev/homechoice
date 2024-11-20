package com.homechoice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Response object for status messages, including status code, message, and timestamp.")
public class MessageResponse {

    @Schema(description = "Status code of the response", example = "200")
    private Integer status;

    @Schema(description = "Detailed message about the response", example = "Operation completed successfully.")
    private String message;

    @Schema(description = "Timestamp when the response was generated", example = "2024-11-11T12:00:00")
    private LocalDateTime timestamp;
}
