package com.rodem.shop.search;

import java.util.Map;

public record IndexStatusResponse(
        boolean exists,
        Map<String, Object> settings,
        Map<String, Object> mapping
) {
}
