package com.rodem.shop.common;

import lombok.Data;

@Data
public class ResponseEntity<T> {
    private int status;
    private T data;
    private long count;

    public ResponseEntity(int status, T data, long count) {
        this.status = status;
        this.data = data;
        this.count = count;
    }
}
