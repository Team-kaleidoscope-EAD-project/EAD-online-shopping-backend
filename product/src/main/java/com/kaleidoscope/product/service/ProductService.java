package com.kaleidoscope.product.service;


import com.kaleidoscope.product.dto.ProductVariantDTO;

import com.kaleidoscope.product.exception.InvalidProductException;
import com.kaleidoscope.product.exception.ProductNotFoundException;
import com.kaleidoscope.product.filter.service.ProductFilterService;

import com.kaleidoscope.product.model.Product;
import com.kaleidoscope.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ProductService {

    public ProductService(ProductRepository productRepository, ProductFilterService filterService){
        this.productRepository = productRepository;
        this.filterService = filterService;
    }
    @Autowired
    private ProductRepository productRepository;
    private final ProductFilterService filterService;


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
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new InvalidProductException("Product name cannot be empty.");
        }
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
        }).orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
    }

    // Delete a product by ID
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with ID " + id + " not found");
        }
        productRepository.deleteById(id);
    }

    // Retrieve a product variant by SKU
    public Optional<ProductVariantDTO> getProductBySku(String sku) {
        return Optional.ofNullable(productRepository.findAll().stream()
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
                                    dto.setPrice(product.getPrice());
                                    dto.setBrand(product.getBrand());
                                    return dto;
                                })
                        )
                )

                .findFirst().orElseThrow(() -> new ProductNotFoundException("Product with SKU " + sku + " not found")));

    }

    // Retrieve products based on filters. This will return products that match all the filters
    public List<Product> getFilteredProducts(
            List<String> categories,
            List<String> colors,
            List<String> brands,
            List<String> sizes,
            Double minPrice,
            Double maxPrice) {

                

        return productRepository.findAll().stream()
                .filter(product -> (categories == null || categories.isEmpty() || categories.contains(product.getCategory())))
                .filter(product -> (colors == null || colors.isEmpty() || product.getVariants().stream()
                        .anyMatch(variant -> colors.contains(variant.getColor()))))
                .filter(product -> (brands == null || brands.isEmpty() || brands.contains(product.getName()))) // Assuming name reflects brand
                .filter(product -> (sizes == null || sizes.isEmpty() || product.getVariants().stream()
                        .flatMap(variant -> variant.getSizes().stream())
                        .anyMatch(size -> sizes.contains(size.getSize()))))
                .filter(product -> (minPrice == null || product.getPrice() >= minPrice))
                .filter(product -> (maxPrice == null || product.getPrice() <= maxPrice))
                .collect(Collectors.toList());
    }
}

