package com.kaleidoscope.product.dto;

import lombok.Data;

@Data
public class ProductVariantDTO {
    private String productId;
    private String productName;
    private String color;
    private String size;
    private String category;
    private String sku;
    private double price;
}
