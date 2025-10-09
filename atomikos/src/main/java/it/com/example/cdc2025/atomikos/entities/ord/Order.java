package it.com.example.cdc2025.atomikos.entities.ord;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_order")
@Data                 // Genera Getter, Setter, toString, equals e hashCode
@Builder              // Genera un builder pattern (utile)
@NoArgsConstructor    // Genera il costruttore senza argomenti (necessario per JPA)
@AllArgsConstructor   // Genera il costruttore con tutti gli argomenti
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemCode;

    private int requestedQuantity;

    // Lo stato è un valore predefinito che possiamo inizializzare
    @Builder.Default
    private String status = "CREATED";

    public Order(String itemCode, int requestedQuantity) {
        this.itemCode = itemCode;
        this.requestedQuantity = requestedQuantity;
    }
}
