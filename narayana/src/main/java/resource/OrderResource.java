// java
package resource;

import dto.OrderRequest;
import entity.ord.Order;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import service.OrderService;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private static final Logger LOG = Logger.getLogger(OrderResource.class);

    @Inject
    OrderService orderService;

    @POST
    @Path("/place")
    public Response place(OrderRequest req) {
        LOG.infof("Received order request: itemCode=%s, requestedQuantity=%d", req.itemCode, req.requestedQuantity);
        try {
            Order order = orderService.placeOrder(req.itemCode, req.requestedQuantity);
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
