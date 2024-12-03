package com.kaleidoscope.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDto {
    private String sku;
    private int quantity;

    @Override
    public String toString() {
        return "InventoryUpdateDTO{productId='" + sku + "', quantity=" + quantity + "}";
    }
    }
