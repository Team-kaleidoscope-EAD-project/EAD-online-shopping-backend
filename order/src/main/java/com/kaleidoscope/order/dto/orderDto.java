package com.kaleidoscope.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class orderDto {
    private int id;
    private String status;
    private Float totalAmount;
    private Integer userId;
    private LocalDate orderDate;
    private Float discountAmount;

}
