package it.com.example.cdc2025.atomikos.services;

import it.com.example.cdc2025.atomikos.entities.inv.InventoryItem;
import it.com.example.cdc2025.atomikos.entities.ord.Order;
import it.com.example.cdc2025.atomikos.repos.inv.InventoryRepository;
import it.com.example.cdc2025.atomikos.repos.ord.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final InventoryRepository inventoryRepo;
    private final OrderRepository orderRepo;

    public OrderService(InventoryRepository inventoryRepo, OrderRepository orderRepo) {
        this.inventoryRepo = inventoryRepo;
        this.orderRepo = orderRepo;
    }

    @Transactional
    public Order placeOrder(String itemCode, int requestedQuantity) {
        // 1. Check inventory availability
        InventoryItem item = inventoryRepo.findByItemCode(itemCode);

        if (item == null) {
            throw new RuntimeException("Item not found: " + itemCode);
        }

        if (item.getQuantity() < requestedQuantity) {
            throw new RuntimeException("Insufficient stock for " + itemCode +
                    ". Available: " + item.getQuantity() + ", Requested: " + requestedQuantity);
        }

        // 2. Decrease inventory (writes to inventory DB)
        item.setQuantity(item.getQuantity() - requestedQuantity);
        inventoryRepo.save(item);

        // 3. Create order (writes to orders DB)
        Order order = Order.builder()
                .itemCode(itemCode)
                .requestedQuantity(requestedQuantity)
                .status("COMPLETED")
                .build();

        return orderRepo.save(order);
    }
}
