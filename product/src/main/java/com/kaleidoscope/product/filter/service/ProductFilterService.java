package com.kaleidoscope.product.filter.service;

import com.kaleidoscope.product.filter.ProductFilter;
import com.kaleidoscope.product.model.Product;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductFilterService {

    public List<Product> applyFilters(List<Product> products, List<ProductFilter> filters) {
        for (ProductFilter filter : filters) {
            products = filter.apply(products);
        }
        return products;
    }
}
