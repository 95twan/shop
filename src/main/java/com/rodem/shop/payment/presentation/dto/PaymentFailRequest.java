package com.rodem.shop.payment.presentation.dto;

import com.rodem.shop.payment.application.dto.PaymentFailCommand;

public record PaymentFailRequest(
        String orderId,
        String paymentKey,
        String code,
        String message,
        Long amount,
        String rawPayload
) {
    public PaymentFailCommand toCommand() {
        return new PaymentFailCommand(orderId, paymentKey, code, message, amount, rawPayload);
    }
}
