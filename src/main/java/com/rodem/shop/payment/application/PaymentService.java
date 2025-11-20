package com.rodem.shop.payment.application;

import com.rodem.shop.common.ResponseEntity;
import com.rodem.shop.payment.application.dto.PaymentCommand;
import com.rodem.shop.payment.application.dto.PaymentInfo;
import com.rodem.shop.payment.client.TossPaymentClient;
import com.rodem.shop.payment.client.dto.TossPaymentResponse;
import com.rodem.shop.payment.domain.Payment;
import com.rodem.shop.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TossPaymentClient tossPaymentClient;

    public ResponseEntity<List<PaymentInfo>> findAll(Pageable pageable) {
        Page<Payment> page = paymentRepository.findAll(pageable);
        List<PaymentInfo> payments = page.stream().map(PaymentInfo::from).toList();
        return new ResponseEntity<>(HttpStatus.OK.value(), payments, page.getTotalElements());
    }

    public ResponseEntity<PaymentInfo> confirm(PaymentCommand command) {
        TossPaymentResponse tossPayment = tossPaymentClient.confirm(command);
        Payment payment = Payment.create(
                tossPayment.paymentKey(),
                tossPayment.orderId(),
                tossPayment.totalAmount()
        );
        LocalDateTime approvedAt = tossPayment.approvedAt() != null ? tossPayment.approvedAt().toLocalDateTime() : null;
        LocalDateTime requestedAt = tossPayment.requestedAt() != null ? tossPayment.requestedAt().toLocalDateTime() : null;
        payment.markConfirmed(tossPayment.method(), approvedAt, requestedAt);

        Payment saved = paymentRepository.save(payment);

        return new ResponseEntity<>(HttpStatus.CREATED.value(), PaymentInfo.from(saved), 1);
    }

}
