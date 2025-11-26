package com.rodem.shop.order.presentation.dto;


import com.rodem.shop.order.application.dto.OrderCommand;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderRequest(
        String productId,
        String memberId
) {
    public OrderCommand toCommand() {
        UUID product = productId != null ? UUID.fromString(productId) : null;
        UUID member = memberId != null ? UUID.fromString(memberId) : null;
        return new OrderCommand(product, member);
    }
}
