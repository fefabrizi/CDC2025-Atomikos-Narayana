// java
package entity.ord;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "item_code", nullable = false)
    public String itemCode;

    @Column(name = "requested_quantity", nullable = false)
    public int requestedQuantity;

    @Column(name = "status")
    public String status = "CREATED";
}
