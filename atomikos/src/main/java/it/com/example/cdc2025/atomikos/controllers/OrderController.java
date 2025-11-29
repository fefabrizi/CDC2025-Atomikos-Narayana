package it.com.example.cdc2025.atomikos.controllers;

import it.com.example.cdc2025.atomikos.entities.ord.Order;
import it.com.example.cdc2025.atomikos.models.OrderRequest;
import it.com.example.cdc2025.atomikos.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest request) {
        try {
            log.info("Received order request: {}", request);
            Order order = orderService.placeOrder(
                    request.getItemCode(),
                    request.getRequestedQuantity()
            );
            log.info("Order placed successfully: {}", order);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            log.error("Error placing order: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}