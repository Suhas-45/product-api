package com.example.productapi.controller;

import com.example.productapi.dto.request.ProductRequest;
import com.example.productapi.dto.response.ItemResponse;
import com.example.productapi.dto.response.ProductResponse;
import com.example.productapi.service.ItemService;
import com.example.productapi.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.productapi.dto.request.ItemRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final ItemService itemService;

    public ProductController(
            ProductService productService,
            ItemService itemService) {
        this.productService = productService;
        this.itemService = itemService;
    }


    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
        Pageable pageable) {

        return ResponseEntity.ok(
                productService.getAllProducts(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productService.getProductById(id)
        );
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {

        return ResponseEntity.ok(
                productService.updateProduct(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<ItemResponse>> getItems(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                itemService.getItemsByProductId(id)
        );
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<ItemResponse> createItem(
        @PathVariable Long id,
        @Valid @RequestBody ItemRequest request) {

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(itemService.createItem(id, request));
    }
}