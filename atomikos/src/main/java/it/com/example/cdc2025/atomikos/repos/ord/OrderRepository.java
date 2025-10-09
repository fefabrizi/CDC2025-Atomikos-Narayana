package it.com.example.cdc2025.atomikos.repos.ord;

import it.com.example.cdc2025.atomikos.entities.ord.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Nessun metodo personalizzato necessario per l'esempio
}
