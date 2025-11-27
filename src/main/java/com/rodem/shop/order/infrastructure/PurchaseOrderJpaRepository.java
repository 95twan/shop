package com.rodem.shop.order.infrastructure;

import com.rodem.shop.order.domain.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseOrderJpaRepository extends JpaRepository<PurchaseOrder, UUID> {
}
