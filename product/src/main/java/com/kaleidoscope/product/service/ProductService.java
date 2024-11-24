package com.kaleidoscope.product.service;


import com.kaleidoscope.product.dto.ProductVariantDTO;

import com.kaleidoscope.product.model.Product;
import com.kaleidoscope.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.util.UUID;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    // Retrieve all products

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    // Retrieve a product by ID
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }



    // Create a new product
    public Product createProduct(Product product) {
        // Generate SKU for each size variant before saving
        generateSkus(product);
        return productRepository.save(product);
    }

    // Generate SKU for each size variant
    private void generateSkus(Product product) {
        for (Product.Variant variant : product.getVariants()) {
            for (Product.Size size : variant.getSizes()) {
                // Create a unique SKU for each size (color + size + unique identifier)
                String sku = generateSku(variant.getColor(), size.getSize());
                size.setSku(sku);
            }
        }
    }

    // Generate a unique SKU
    private String generateSku(String color, String size) {
        // Combine color and size to form the SKU and append a unique identifier
        return color.substring(0, 3).toUpperCase() + "-" + size.toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

    // Update an existing product
    public Product updateProduct(String id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setCategory(updatedProduct.getCategory());
            product.setPrice(updatedProduct.getPrice());

            product.setImageUrl(updatedProduct.getImageUrl());
            return productRepository.save(product);
        }).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // Delete a product by ID
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    // Retrieve a product variant by SKU
    public Optional<ProductVariantDTO> getProductBySku(String sku) {
        return productRepository.findAll().stream()
                .flatMap(product -> product.getVariants().stream()
                        .flatMap(variant -> variant.getSizes().stream()
                                .filter(size -> size.getSku().equals(sku))
                                .map(size -> {
                                    ProductVariantDTO dto = new ProductVariantDTO();
                                    dto.setCategory(product.getCategory());
                                    dto.setProductId(product.getId());
                                    dto.setProductName(product.getName());
                                    dto.setColor(variant.getColor());
                                    dto.setSize(size.getSize());
                                    dto.setSku(size.getSku());
                                    dto.setPrice(product.getPrice());  // Assuming the price is at the product level
                                    return dto;
                                })
                        )
                )
                .findFirst();
    }}

