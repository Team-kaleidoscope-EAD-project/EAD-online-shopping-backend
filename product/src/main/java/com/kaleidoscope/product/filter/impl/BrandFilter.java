package com.kaleidoscope.product.filter.impl;

import com.kaleidoscope.product.filter.ProductFilter;
import com.kaleidoscope.product.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class BrandFilter implements ProductFilter {
    private final String brand;

    public BrandFilter(String brand) {
        this.brand = brand;
    }

    @Override
    public List<Product> apply(List<Product> products) {
        return products.stream()
                .filter(product -> product.getBrand() != null && product.getBrand().equalsIgnoreCase(brand))
                .collect(Collectors.toList());
    }

}
