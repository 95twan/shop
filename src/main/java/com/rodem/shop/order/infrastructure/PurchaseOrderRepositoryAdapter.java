package com.rodem.shop.order.infrastructure;

import com.rodem.shop.order.domain.PurchaseOrderRepository;
import com.rodem.shop.order.domain.PurchaseOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PurchaseOrderRepositoryAdapter implements PurchaseOrderRepository {

    private final PurchaseOrderJpaRepository purchaseOrderJpaRepository;

    @Override
    public Page<PurchaseOrder> findAll(Pageable pageable) {
        return purchaseOrderJpaRepository.findAll(pageable);
    }

    @Override
    public Optional<PurchaseOrder> findById(UUID id) {
        return purchaseOrderJpaRepository.findById(id);
    }

    @Override
    public PurchaseOrder save(PurchaseOrder order) {
        return purchaseOrderJpaRepository.save(order);
    }
}
