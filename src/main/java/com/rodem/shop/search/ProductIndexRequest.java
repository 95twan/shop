package com.rodem.shop.search;


public record ProductIndexRequest(
        String name,
        String brand,
        String category,
        Integer price
) {
}
