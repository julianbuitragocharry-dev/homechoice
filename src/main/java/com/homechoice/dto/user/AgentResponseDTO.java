package com.homechoice.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentResponseDTO {
    private String name;
    private String email;
    private String phone;
}
