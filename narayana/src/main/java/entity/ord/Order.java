package entity.ord;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer_order")
public class Order extends PanacheEntity {
    public String itemCode;
    public int requestedQuantity;
    public String status = "CREATED";
}