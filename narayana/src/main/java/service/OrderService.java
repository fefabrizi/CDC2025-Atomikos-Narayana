package service;

import entity.inv.InventoryItem;
import entity.ord.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import repository.inv.InventoryRepository;
import repository.ord.OrderRepository;

@ApplicationScoped
public class OrderService {

    @Inject
    InventoryRepository inventoryRepo;

    @Inject
    OrderRepository orderRepo;

    @Transactional
    public Order placeOrder(String itemCode, int requestedQuantity) {
        InventoryItem item = inventoryRepo.findByItemCode(itemCode);
        if (item == null) {
            throw new RuntimeException("Item not found: " + itemCode);
        }
        if (item.quantity < requestedQuantity) {
            throw new RuntimeException("Insufficient stock for " + itemCode +
                    ". Available: " + item.quantity + ", Requested: " + requestedQuantity);
        }

        item.quantity -= requestedQuantity;

        Order order = new Order();
        order.itemCode = itemCode;
        order.requestedQuantity = requestedQuantity;
        order.status = "COMPLETED";
        orderRepo.persist(order);

        return order;
    }
}
