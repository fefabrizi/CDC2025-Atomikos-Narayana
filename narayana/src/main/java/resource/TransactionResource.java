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
    public String execute(@QueryParam("isFailed") boolean isFailed) {
        String status = null;
        try {
            status = service.performBusinessLogic(isFailed);
            return status;
        } catch (Exception exception) {
            return "Transazione fallita e rollback eseguito: " + exception.getMessage();
        }
    }
}
