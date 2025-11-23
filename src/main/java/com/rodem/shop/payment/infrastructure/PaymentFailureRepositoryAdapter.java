package com.rodem.shop.payment.infrastructure;

import com.rodem.shop.payment.domain.PaymentFailure;
import com.rodem.shop.payment.domain.PaymentFailureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentFailureRepositoryAdapter implements PaymentFailureRepository {

    private final PaymentFailureJpaRepository paymentFailureJpaRepository;

    @Override
    public PaymentFailure save(PaymentFailure failure) {
        return paymentFailureJpaRepository.save(failure);
    }
}
