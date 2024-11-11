package com.homechoice.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String nit;
    private String email;
    private String password;
}
