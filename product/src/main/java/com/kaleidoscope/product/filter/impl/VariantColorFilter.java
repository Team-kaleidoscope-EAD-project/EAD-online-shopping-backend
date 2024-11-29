package com.kaleidoscope.product.filter.impl;

import com.kaleidoscope.product.filter.ProductFilter;
import com.kaleidoscope.product.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class VariantColorFilter implements ProductFilter {

    private final String color;

    public VariantColorFilter(String color) {
        this.color = color;
    }

    @Override
    public List<Product> apply(List<Product> products) {
        return products.stream()
                .filter(product -> product.getVariants().stream()
                        .anyMatch(variant -> variant.getColor().equalsIgnoreCase(color)))
                .collect(Collectors.toList());
    }
}
