package com.kaleidoscope.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class orderItemDto {
    private int id;
    private int orderId;
    private Float price;
    private int quantity;
    private int productId;
}
