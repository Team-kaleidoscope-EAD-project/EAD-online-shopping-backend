package com.kaleidoscope.product.filter.impl;

import com.kaleidoscope.product.filter.ProductFilter;
import com.kaleidoscope.product.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryFilter implements ProductFilter {

    private final String category;

    public CategoryFilter(String category) {
        this.category = category;
    }

    @Override
    public List<Product> apply(List<Product> products) {
        return products.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }
}
