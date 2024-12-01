package com.kaleidoscope.product.controller;


import com.kaleidoscope.product.dto.ProductVariantDTO;
import com.kaleidoscope.product.model.Product;
import com.kaleidoscope.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api/v1/product")



public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/test")
    public Mono<String> testItems() {
        return Mono.just("Product is working");
    }


    @GetMapping("/")

    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }


    // GET /api/products/{id} - Retrieve a product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST /api/products - Create a new product
    @PostMapping("/addproduct")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    // PUT /api/products/{id} - Update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        try {
            Product product = productService.updateProduct(id, updatedProduct);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE /api/products/{id} - Delete a product by ID

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    // GET /api/products/category/{category} - Retrieve products by category
    @GetMapping("/sku/{sku}")
    public ResponseEntity<ProductVariantDTO> getProductBySku(@PathVariable String sku) {
        Optional<ProductVariantDTO> productVariant = productService.getProductBySku(sku);
        return productVariant.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    // Get /api/products/filter - Filter products by category, color, brand, size, price
    // Ex /filter?categories=Clothing&categories=Cap&colors=Red&colors=Blue&brands=Nike&brands=Adidas&sizes=M&sizes=L&minPrice=50&maxPrice=500
    // Can Filter different same attributes with multiple values
    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProducts(
            @RequestParam(required = false) List<String> categories,
            @RequestParam(required = false) List<String> colors,
            @RequestParam(required = false) List<String> brands,
            @RequestParam(required = false) List<String> sizes,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

                System.out.println(categories);
        List<Product> filteredProducts = productService.getFilteredProducts(categories, colors, brands, sizes, minPrice, maxPrice);
        return ResponseEntity.ok(filteredProducts);
    }


}
