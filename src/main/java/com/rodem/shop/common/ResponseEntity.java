package com.rodem.shop.common;


public record ResponseEntity<T>(
        int status,
        T data,
        long count
) {
}
