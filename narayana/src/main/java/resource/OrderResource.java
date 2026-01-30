// java
package resource;

import dto.OrderRequest;
import entity.inv.InventoryItem;
import entity.ord.Order;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import service.OrderService;
import repository.inv.InventoryRepository;
import java.util.List;
import java.util.Random;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private static final Logger LOG = Logger.getLogger(OrderResource.class);

    @Inject
    OrderService orderService;

    @Inject
    InventoryRepository inventoryRepository;

    @GET
    @Path("/place")
    public Response placeRandom() {
        try {
            // Assume you have an injected InventoryService
            List<InventoryItem> items = inventoryRepository.findAll();
            if (items.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(java.util.Map.of("error", "No items in inventory"))
                        .build();
            }
            InventoryItem randomItem = items.get(new java.util.Random().nextInt(items.size()));
            int randomQuantity = 1 + new java.util.Random().nextInt(5);

            Order order = orderService.placeOrder(randomItem.getItemCode(), randomQuantity);
            LOG.infof("Order placed successfully: id=%s, itemCode=%s, requestedQuantity=%d, status=%s",
                    order.id, order.itemCode, order.requestedQuantity, order.status);
            return Response.ok(order).build();
        } catch (RuntimeException e) {
            LOG.errorf("Error placing order: %s", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(java.util.Map.of("error", e.getMessage()))
                    .build();
        }
    }

}
