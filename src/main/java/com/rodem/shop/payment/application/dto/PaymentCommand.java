package com.rodem.shop.payment.application.dto;

public record PaymentCommand(
        String paymentKey,
        String orderId,
        Long amount
) {
}
