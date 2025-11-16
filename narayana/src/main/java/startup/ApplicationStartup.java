package startup;

import entity.inv.InventoryItem;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import repository.inv.InventoryRepository;
import service.TransactionalService;

@ApplicationScoped
public class ApplicationStartup {

    @Inject
    InventoryRepository inventoryRepository;

    @Inject
    TransactionalService orderService;

    void onStart(@Observes StartupEvent ev) {
        System.out.println("=== Application Starting - Initializing Test Data ===");

        // Initialize data in a separate transaction
        initializeData();

        // Run tests (each test runs in its own transaction)
        runTests();
    }

    @Transactional
    void initializeData() {
        InventoryItem laptop = new InventoryItem();
        laptop.itemCode = "LAPTOP-X";
        laptop.quantity = 10;
        inventoryRepository.persist(laptop);
        System.out.println("Initialized inventory: LAPTOP-X with quantity 10");
    }

    void runTests() {
        // --- TEST 1: Successful transaction ---
        try {
            System.out.println("\n=== TEST 1: Successful Transaction ===");
            orderService.processOrder("LAPTOP-X", 2, false);
        } catch (Exception e) {
            System.err.println("TEST 1 FAILED: " + e.getMessage());
        }

        // --- TEST 2: Forced rollback ---
        try {
            System.out.println("\n=== TEST 2: Forced Rollback ===");
            orderService.processOrder("LAPTOP-X", 3, true);
        } catch (Exception e) {
            System.err.println("TEST 2 handled: Transaction rolled back - " + e.getMessage());
        }

        // --- FINAL VERIFICATION ---
        verifyResults();
    }

    @Transactional
    void verifyResults() {
        InventoryItem finalItem = inventoryRepository.findByItemCode("LAPTOP-X");
        System.out.println("\n=== FINAL VERIFICATION ===");
        System.out.println("Final stock for LAPTOP-X: " + finalItem.quantity);
        System.out.println("Expected: 8 (10 - 2 from TEST 1, TEST 2 rolled back)");
    }
}
