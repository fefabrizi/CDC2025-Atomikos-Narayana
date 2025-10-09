package it.com.example.cdc2025.atomikos.repos.inv;

import it.com.example.cdc2025.atomikos.entities.inv.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItem, String> {

    // Metodo per cercare l'articolo
    InventoryItem findByItemCode(String itemCode);
}
