package repository.inv;

import entity.inv.InventoryItem;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.inject.Inject;

@ApplicationScoped
public class InventoryRepository {

    @Inject
    @PersistenceUnit("inventory")
    EntityManager em;

    public void persist(InventoryItem item) {
        em.persist(item);
    }

    public InventoryItem findByItemCode(String itemCode) {
        return em.createQuery("SELECT i FROM InventoryItem i WHERE i.itemCode = :code", InventoryItem.class)
                .setParameter("code", itemCode)
                .getSingleResult();
    }
}