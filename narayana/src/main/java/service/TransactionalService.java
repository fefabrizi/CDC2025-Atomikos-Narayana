package service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TransactionalService {

    @Transactional
    public String performBusinessLogic(boolean isFailed) {
        System.out.println("Inizio logica di business in transazione...");

        // Simulazione operazioni
        System.out.println("Operazione 1 completata.");
        System.out.println("Operazione 2 completata.");

        if (isFailed) {
            // Simula errore che causerà rollback
            throw new RuntimeException("Errore simulato! Rollback in corso...");
        }

        String success = "Tutte le operazioni completate. Commit automatico.";
        System.out.println(success);
        return success;
    }
}
