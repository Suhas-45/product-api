package com.example.productapi.service;

import com.example.productapi.dto.request.ItemRequest;
import com.example.productapi.dto.response.ItemResponse;
import com.example.productapi.entity.Item;
import com.example.productapi.entity.Product;
import com.example.productapi.exception.ResourceNotFoundException;
import com.example.productapi.repository.ItemRepository;
import com.example.productapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;

    public ItemService(
            ItemRepository itemRepository,
            ProductRepository productRepository) {
        this.itemRepository = itemRepository;
        this.productRepository = productRepository;
    }

    public List<ItemResponse> getItemsByProductId(Long productId) {

        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException(
                    "Product not found with id: " + productId
            );
        }

        return itemRepository.findByProductId(productId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ItemResponse createItem(
            Long productId,
            ItemRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: " + productId
                        ));

        Item item = Item.builder()
                .product(product)
                .quantity(request.quantity())
                .build();

        return toResponse(itemRepository.save(item));
    }

    private ItemResponse toResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getQuantity()
        );
    }
}