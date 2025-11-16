package entity.inv;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_item")
public class InventoryItem extends PanacheEntityBase {

    @Id
    public String itemCode;
    public int quantity;

    public static InventoryItem findByItemCode(String itemCode) {
        return find("itemCode", itemCode).firstResult();
    }
}