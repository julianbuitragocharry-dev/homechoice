package com.homechoice.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponseDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String nit;
    private String email;
}
