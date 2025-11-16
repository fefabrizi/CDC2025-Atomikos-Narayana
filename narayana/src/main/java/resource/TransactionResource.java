package resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import service.TransactionalService;

@Path("/transaction")
public class TransactionResource {

    @Inject
    TransactionalService service;

    @GET
    public String execute(
            @QueryParam("itemCode") String itemCode,
            @QueryParam("quantity") int quantity,
            @QueryParam("shouldFail") boolean shouldFail) {
        try {
            service.processOrder(itemCode, quantity, shouldFail);
            return "Transazione completata con successo per " + itemCode;
        } catch (Exception exception) {
            return "Transazione fallita e rollback eseguito: " + exception.getMessage();
        }
    }
}
