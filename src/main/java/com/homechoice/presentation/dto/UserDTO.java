package com.homechoice.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDTO {
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String nit;
    private String email;
    private String password;
    private List<Integer> roles;
}
