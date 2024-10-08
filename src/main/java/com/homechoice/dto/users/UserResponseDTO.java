package com.homechoice.dto.users;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponseDTO {
    private Integer id;
    private String name;
    private String phone;
    private String address;
    private String nit;
    private String email;
}
