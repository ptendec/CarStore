package com.tech.leasing.dto;

import lombok.Data;

@Data
public class BookingDTO {
    private Long userId;
    private Long carId;
    private String statusName;
}
