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


    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
