package com.homechoice.dto.users;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AgentResponseDTO {
    private String name;
    private String email;
    private String phone;
}
