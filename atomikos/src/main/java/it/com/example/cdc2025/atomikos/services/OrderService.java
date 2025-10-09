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

    // Atomikos gestirà QUESTA transazione come distribuita (2PC)
    @Transactional
    public void processOrder(String itemCode, int requestedQuantity, boolean shouldFail) {

        System.out.println("\n--- INIZIO TRANSAZIONE DISTRIBUITA ---");

        // 1. Logica del Database B (Orders) - Crea l'ordine
        Order newOrder = new Order(itemCode, requestedQuantity);
        orderRepo.save(newOrder);
        System.out.println("1. Successo: Ordine ID " + newOrder.getId() + " creato nel DB Orders.");

        // 2. Logica del Database A (Inventory) - Controlla e aggiorna lo stock
        InventoryItem item = inventoryRepo.findByItemCode(itemCode);

        if (item == null) {
            throw new RuntimeException("ERRORE: Articolo non trovato in magazzino: " + itemCode);
        }

        if (item.getQuantity() < requestedQuantity) {
            // Questo forzerà un ROLLBACK su ENTRAMBI i DB
            throw new RuntimeException("ERRORE: Stock insufficiente. Richiesto: " + requestedQuantity + ", Disponibile: " + item.getQuantity());
        }

        item.setQuantity(item.getQuantity() - requestedQuantity);
        inventoryRepo.save(item);
        System.out.println("2. Successo: Stock aggiornato per " + itemCode + ". Nuovo stock: " + item.getQuantity());

        // 3. Punto di Fallimento Forzato (per il test)
        if (shouldFail) {
            System.out.println("3. Fallimento Forzato! Atomikos eseguirà il ROLLBACK.");
            throw new RuntimeException("ROLLBACK FORZATO!");
        }

        System.out.println("--- COMMIT: Entrambi i DB salvano le modifiche ---");
    }
}
