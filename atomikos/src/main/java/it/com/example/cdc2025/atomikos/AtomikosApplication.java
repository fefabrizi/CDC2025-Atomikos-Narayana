package it.com.example.cdc2025.atomikos;

import it.com.example.cdc2025.atomikos.entities.inv.InventoryItem;
import it.com.example.cdc2025.atomikos.repos.inv.InventoryRepository;
import it.com.example.cdc2025.atomikos.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// Dobbiamo escludere l'AutoConfiguration standard del DataSource di Spring Boot
// perché stiamo usando la configurazione JTA/XA di Atomikos.
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AtomikosApplication implements CommandLineRunner {

	@Autowired
	private OrderService orderService;

	@Autowired
	private InventoryRepository inventoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(AtomikosApplication.class, args);
	}

	@Override
	public void run(String... args) {
		// 1. Inizializza l'inventario (su inventory-db)
		inventoryRepository.save(new InventoryItem("LAPTOP-X", 10));

		// --- TEST 1: Transazione OK (Dovrebbe fare COMMIT) ---
		try {
			orderService.processOrder("LAPTOP-X", 2, false);
		} catch (Exception e) {
			System.err.println("TEST 1 FALLITO con eccezione, ma non dovrebbe: " + e.getMessage());
		}

		// --- TEST 2: Transazione FORZATA a fallire (Dovrebbe fare ROLLBACK) ---
		try {
			orderService.processOrder("LAPTOP-X", 3, true);
		} catch (Exception e) {
			System.err.println("\nTEST 2 gestito: La transazione è stata annullata (ROLLBACK).");
		}

		// --- VERIFICA FINALE ---
		// Lo stock dovrebbe essere 10 - 2 = 8, perché il TEST 2 ha fatto ROLLBACK
		InventoryItem finalItem = inventoryRepository.findByItemCode("LAPTOP-X");
		System.out.println("\n--- VERIFICA FINALE ---");
		System.out.println("Stock finale di LAPTOP-X: " + finalItem.getQuantity());
	}
}
