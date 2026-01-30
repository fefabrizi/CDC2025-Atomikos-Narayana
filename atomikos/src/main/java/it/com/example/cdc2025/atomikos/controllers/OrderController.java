package it.com.example.cdc2025.atomikos.controllers;

import it.com.example.cdc2025.atomikos.entities.ord.Order;
import it.com.example.cdc2025.atomikos.models.OrderRequest;
import it.com.example.cdc2025.atomikos.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import it.com.example.cdc2025.atomikos.entities.inv.InventoryItem;
import it.com.example.cdc2025.atomikos.repos.inv.InventoryRepository;
import java.util.List;
import java.util.Random;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final InventoryRepository inventoryRepository;

    public OrderController(OrderService orderService, InventoryRepository inventoryRepository) {

        this.inventoryRepository = inventoryRepository;
        this.orderService = orderService;

    }


    @GetMapping("/place")
    public ResponseEntity<?> placeOrder() {
        try {
            List<InventoryItem> items = inventoryRepository.findAll();
            if (items.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "No items in inventory"));
            }
            InventoryItem randomItem = items.get(new Random().nextInt(items.size()));
            int randomQuantity = 1 + new Random().nextInt(5); // random quantity 1-5 for an order

            Order order = orderService.placeOrder(
                    randomItem.getItemCode(),
                    randomQuantity
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