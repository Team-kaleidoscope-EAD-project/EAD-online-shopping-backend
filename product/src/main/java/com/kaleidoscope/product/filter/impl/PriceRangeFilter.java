package com.kaleidoscope.product.filter.impl;

import com.kaleidoscope.product.filter.ProductFilter;
import com.kaleidoscope.product.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class PriceRangeFilter implements ProductFilter {

    private final double minPrice;
    private final double maxPrice;

    public PriceRangeFilter(double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    @Override
    public List<Product> apply(List<Product> products) {
        return products.stream()
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
}
