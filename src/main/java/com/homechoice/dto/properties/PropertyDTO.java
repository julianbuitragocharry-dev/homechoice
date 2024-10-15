package com.homechoice.dto.properties;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class PropertyDTO {
    private Integer id;
    private String name;
    private Long area;
    private BigDecimal price;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Boolean status;
    private String description;
    private Integer agent;
    private String concept;
    private String type;
    private List<String> images;
    private List<String> amenities;
}
