package com.example.productapi.service;

import com.example.productapi.dto.request.ProductRequest;
import com.example.productapi.dto.response.ProductResponse;
import com.example.productapi.entity.Product;
import com.example.productapi.exception.ResourceNotFoundException;
import com.example.productapi.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {

        product = Product.builder()
                .id(1L)
                .productName("Laptop")
                .createdBy("system")
                .createdOn(LocalDateTime.now())
                .build();
    }

    @Test
    void getProductById_shouldReturnProduct() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponse response =
                productService.getProductById(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Laptop", response.productName());

        verify(productRepository).findById(1L);
    }

    @Test
    void getProductById_shouldThrowExceptionWhenNotFound() {

        when(productRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.getProductById(99L)
        );

        verify(productRepository).findById(99L);
    }

    @Test
    void createProduct_shouldSaveAndReturnProduct() {

        ProductRequest request =
                new ProductRequest("Laptop");

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        ProductResponse response =
                productService.createProduct(request);

        assertNotNull(response);
        assertEquals("Laptop", response.productName());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void updateProduct_shouldUpdateProduct() {

        ProductRequest request =
                new ProductRequest("Gaming Laptop");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        ProductResponse response =
                productService.updateProduct(1L, request);

        assertEquals(
                "Gaming Laptop",
                response.productName()
        );

        verify(productRepository).findById(1L);
        verify(productRepository).save(product);
    }

    @Test
    void deleteProduct_shouldDeleteProduct() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).findById(1L);
        verify(productRepository).delete(product);
    }
}