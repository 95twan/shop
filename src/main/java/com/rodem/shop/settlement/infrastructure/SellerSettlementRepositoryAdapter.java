package com.rodem.shop.settlement.infrastructure;

import com.rodem.shop.settlement.domain.SellerSettlement;
import com.rodem.shop.settlement.domain.SellerSettlementRepository;
import com.rodem.shop.settlement.domain.SettlementStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SellerSettlementRepositoryAdapter implements SellerSettlementRepository {

    private final SellerSettlementJpaRepository sellerSettlementJpaRepository;

    @Override
    public SellerSettlement save(SellerSettlement settlement) {
        return sellerSettlementJpaRepository.save(settlement);
    }

    @Override
    public List<SellerSettlement> findByStatus(SettlementStatus status) {
        return sellerSettlementJpaRepository.findByStatus(status);
    }

    @Override
    public List<SellerSettlement> findByStatusAndSeller(SettlementStatus status, UUID sellerId) {
        return sellerSettlementJpaRepository.findByStatusAndSellerId(status, sellerId);
    }

    @Override
    public void saveAll(List<SellerSettlement> settlements) {
        sellerSettlementJpaRepository.saveAll(settlements);
    }
}
