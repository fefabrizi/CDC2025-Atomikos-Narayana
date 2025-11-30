package repository.inv;

import entity.inv.InventoryItem;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class InventoryRepository {

    @Inject
    @PersistenceUnit("inventory")
    EntityManager em;

    public void persist(InventoryItem item) {
        em.persist(item);
    }

    public InventoryItem findByItemCode(String itemCode) {
        try {
            return em.createQuery("SELECT i FROM InventoryItem i WHERE i.itemCode = :code", InventoryItem.class)
                    .setParameter("code", itemCode)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
