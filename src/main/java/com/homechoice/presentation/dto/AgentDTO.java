package com.homechoice.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AgentDTO {
    private String name;
    private String phone;
    private String email;
}
