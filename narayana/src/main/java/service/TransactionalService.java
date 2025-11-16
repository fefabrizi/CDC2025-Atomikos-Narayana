package service;

import entity.inv.InventoryItem;
import entity.ord.Order;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import repository.inv.InventoryRepository;
import repository.ord.OrderRepository;

@ApplicationScoped
public class TransactionalService {

    @Inject
    InventoryRepository inventoryRepo;

    @Inject
    OrderRepository orderRepo;

    @Transactional
    public void processOrder(String itemCode, int requestedQuantity, boolean shouldFail) {
        System.out.println("\n--- INIZIO TRANSAZIONE DISTRIBUITA (Narayana) ---");

        // 1. Create order in orders DB
        Order newOrder = new Order();
        newOrder.itemCode = itemCode;
        newOrder.requestedQuantity = requestedQuantity;
        orderRepo.persist(newOrder);
        System.out.println("1. Successo: Ordine ID " + newOrder.id + " creato nel DB Orders.");

        // 2. Update inventory
        InventoryItem item = inventoryRepo.findByItemCode(itemCode);
        if (item == null) {
            throw new RuntimeException("ERRORE: Articolo non trovato: " + itemCode);
        }

        if (item.quantity < requestedQuantity) {
            throw new RuntimeException("ERRORE: Stock insufficiente");
        }

        item.quantity -= requestedQuantity;
        System.out.println("2. Successo: Stock aggiornato. Nuovo stock: " + item.quantity);

        if (shouldFail) {
            System.out.println("3. Fallimento Forzato! Narayana eseguirà il ROLLBACK.");
            throw new RuntimeException("ROLLBACK FORZATO!");
        }

        System.out.println("--- COMMIT: Entrambi i DB salvano le modifiche ---");
    }
}
