package com.rodem.shop.order.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface PurchaseOrderRepository {
    Page<PurchaseOrder> findAll(Pageable pageable);
    Optional<PurchaseOrder> findById(UUID id);
    PurchaseOrder save(PurchaseOrder order);
}
