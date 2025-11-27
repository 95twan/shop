package com.rodem.shop.search;

import java.util.List;

public record ProductSearchResponse(
        long total,
        List<ProductDocument> items
) {
}
