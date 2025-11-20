package com.rodem.shop.payment.presentation.dto;

import com.rodem.shop.payment.application.dto.PaymentCommand;

public record PaymentRequest(
        String paymentKey,
        String orderId,
        Long amount
) {
    public PaymentCommand toCommand() {
        return new PaymentCommand(paymentKey, orderId, amount);
    }
}
