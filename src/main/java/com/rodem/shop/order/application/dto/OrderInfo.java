package com.rodem.shop.order.application.dto;


import com.rodem.shop.order.domain.PurchaseOrder;
import com.rodem.shop.order.domain.PurchaseOrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderInfo(
        UUID id,
        UUID productId,
        UUID sellerId,
        UUID memberId,
        BigDecimal amount,
        PurchaseOrderStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static OrderInfo from(PurchaseOrder purchaseOrder) {
        return new OrderInfo(
                purchaseOrder.getId(),
                purchaseOrder.getProductId(),
                purchaseOrder.getSellerId(),
                purchaseOrder.getMemberId(),
                purchaseOrder.getAmount(),
                purchaseOrder.getStatus(),
                purchaseOrder.getCreatedAt(),
                purchaseOrder.getUpdatedAt()
        );
    }
}
