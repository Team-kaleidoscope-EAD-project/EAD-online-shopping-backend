package com.kaleidoscope.product.repository;

import com.kaleidoscope.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    List<Product> findByCategory(String category);

    // Find products by name
    List<Product> findByNameContainingIgnoreCase(String name);

    // Find top 3 products by name containing the search term (case insensitive)
    List<Product> findTop5ByNameContainingIgnoreCase(String name);
}
