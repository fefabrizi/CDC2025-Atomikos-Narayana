package it.com.example.cdc2025.atomikos.entities.inv;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventory_item")
@Data                 // Genera Getter, Setter, toString, equals e hashCode
@Builder              // Genera un builder pattern (utile)
@NoArgsConstructor    // Genera il costruttore senza argomenti (necessario per JPA)
@AllArgsConstructor   // Genera il costruttore con tutti gli argomenti
public class InventoryItem {

    @Id
    private String itemCode; // Chiave primaria, es: "PROD101"

    private int quantity;    // Quantità disponibile
}
