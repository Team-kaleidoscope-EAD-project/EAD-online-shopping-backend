package com.kaleidoscope.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateDto {
    private String sku;
    private int quantity;
}
