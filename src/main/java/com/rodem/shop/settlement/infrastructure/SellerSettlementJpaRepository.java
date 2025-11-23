package com.rodem.shop.settlement.infrastructure;

import com.rodem.shop.settlement.domain.SellerSettlement;
import com.rodem.shop.settlement.domain.SettlementStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SellerSettlementJpaRepository extends JpaRepository<SellerSettlement, UUID> {
    List<SellerSettlement> findByStatus(SettlementStatus status);

    List<SellerSettlement> findByStatusAndSellerId(SettlementStatus status, UUID sellerId);
}
