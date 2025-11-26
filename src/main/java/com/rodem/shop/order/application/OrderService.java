package com.rodem.shop.order.application;

import com.rodem.shop.common.ResponseEntity;
import com.rodem.shop.order.application.dto.OrderCommand;
import com.rodem.shop.order.application.dto.OrderInfo;
import com.rodem.shop.order.domain.OrderRepository;
import com.rodem.shop.order.domain.PurchaseOrder;
import com.rodem.shop.order.domain.PurchaseOrderStatus;
import com.rodem.shop.product.domain.Product;
import com.rodem.shop.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public ResponseEntity<OrderInfo> create(OrderCommand command) {
        if (command.productId() == null || command.memberId() == null) {
            throw new IllegalArgumentException("productId and memberId are required");
        }
        Product product = productRepository.findById(command.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + command.productId()));

        PurchaseOrder order = PurchaseOrder.create(
                product.getId(),
                product.getSellerId(),
                command.memberId(),
                product.getPrice()
        );
        PurchaseOrder saved = orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.CREATED.value(), OrderInfo.from(saved), 1);
    }

    public ResponseEntity<List<OrderInfo>> findAll(Pageable pageable) {
        Page<PurchaseOrder> page = orderRepository.findAll(pageable);
        List<OrderInfo> infos = page.stream().map(OrderInfo::from).toList();
        return new ResponseEntity<>(HttpStatus.OK.value(), infos, page.getTotalElements());
    }

    public ResponseEntity<OrderInfo> statusChange(UUID id, PurchaseOrderStatus status) {
        PurchaseOrder order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        order.setStatus(status);
        orderRepository.save(order);
        return new ResponseEntity<>(HttpStatus.OK.value(), OrderInfo.from(order), 1);
    }

    public PurchaseOrder findById(String id) {
        UUID orderId = UUID.fromString(id);
        return orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    public void markPaid(PurchaseOrder order) {
        order.markPaid();
        orderRepository.save(order);
    }
}
