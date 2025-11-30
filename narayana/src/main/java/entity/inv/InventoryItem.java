// java
package entity.inv;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory_item")
public class InventoryItem {

    @Id
    @Column(name = "item_code", nullable = false)
    public String itemCode;

    @Column(name = "quantity", nullable = false)
    public int quantity;
}
