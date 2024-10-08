package com.homechoice.presentation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class PropertyDTO {
    private String name;
    private Long area;
    private BigDecimal price;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Boolean status;
    private String description;
    private Integer user;
    private Integer concept;
    private Integer type;
    private List<Integer> amenities;
}
