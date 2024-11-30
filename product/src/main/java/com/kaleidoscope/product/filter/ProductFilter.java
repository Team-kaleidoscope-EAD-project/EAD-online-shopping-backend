package com.kaleidoscope.product.filter;

import com.kaleidoscope.product.model.Product;

import java.util.List;

public interface ProductFilter {
    List<Product> apply(List<Product> products);
}
