package com.homechoice.dto.users;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AgentsResponseDTO {
    private Integer id;
    private String name;
}
