package com.kaleidoscope.product.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.List;

@Data
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private String category;
    private double price;

    private String brand;


    private String imageUrl;

    private List<Variant> variants;

    @Data
    public static class Variant {
        private String color;
        private String imageUrl;
        private List<Size> sizes;
    }

    @Data
    public static class Size {
        private String size;
        private String sku;  // SKU is a string
    }  

}
