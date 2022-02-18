package com.tech.leasing.dto;

import lombok.Data;

@Data
public class CarDTO {
    private Long carId;
    private String mark;
    private String description;
    private String man_year;
    private Long mileage;
}
