package repository.ord;

import entity.ord.Order;
import io.quarkus.hibernate.orm.PersistenceUnit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.inject.Inject;

@ApplicationScoped
public class OrderRepository {

    @Inject
    @PersistenceUnit("orders")
    EntityManager em;

    public void persist(Order order) {
        em.persist(order);
    }
}