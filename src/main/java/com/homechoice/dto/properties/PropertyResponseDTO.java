package com.homechoice.dto.properties;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class PropertyResponseDTO {
    private Integer id;
    private String name;
    private Long area;
    private BigDecimal price;
    private Boolean status;
    private String concept;
    private String type;
    private List<String> images;
}
