package com.rodem.shop.order.application.dto;

import java.util.UUID;

public record OrderCommand(
        UUID productId,
        UUID memberId
) {
}
