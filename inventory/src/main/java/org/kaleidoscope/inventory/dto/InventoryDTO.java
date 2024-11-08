package org.kaleidoscope.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private int inventoryItemId;
    private int productId;
    private String color;
    private String size;
    private Integer quantity;
}
