package com.rodem.shop.order.presentation;

import com.rodem.shop.common.ResponseEntity;
import com.rodem.shop.order.application.OrderService;
import com.rodem.shop.order.application.dto.OrderInfo;
import com.rodem.shop.order.domain.PurchaseOrderStatus;
import com.rodem.shop.order.presentation.dto.OrderRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.v1}/order")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "상품과 구매자 정보를 바탕으로 주문을 생성한다.")
    @PostMapping
    public ResponseEntity<OrderInfo> create(@RequestBody OrderRequest request) {
        return orderService.create(request.toCommand());
    }

    @Operation(summary = "주문 목록 조회", description = "생성된 주문을 페이지 단위로 조회한다.")
    @GetMapping
    public ResponseEntity<List<OrderInfo>> findAll(Pageable pageable) {
        return orderService.findAll(pageable);
    }

    @Operation(summary = "주문 상태 결제 변경", description = "주문을 결재 상태로 변경한다.")
    @PatchMapping("/{id}/paid")
    public ResponseEntity<OrderInfo> paid(@PathVariable UUID id) {
        return orderService.statusChange(id, PurchaseOrderStatus.PAID);
    }

    @Operation(summary = "주문 상태 취소 변경", description = "주문을 취소 상태로 변경한다.")
    @PatchMapping("/{id}/canceled")
    public ResponseEntity<OrderInfo> canceled(@PathVariable UUID id) {
        return orderService.statusChange(id, PurchaseOrderStatus.CANCELLED);
    }
}
