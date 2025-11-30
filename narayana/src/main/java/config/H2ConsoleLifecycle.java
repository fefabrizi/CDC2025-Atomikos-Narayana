// java
package config;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.h2.tools.Server;
import org.jboss.logging.Logger;

@ApplicationScoped
public class H2ConsoleLifecycle {

    private static final Logger LOG = Logger.getLogger(H2ConsoleLifecycle.class);

    private Server webServer;

    void onStart(@Observes StartupEvent ev) {
        boolean enabled = Boolean.parseBoolean(System.getProperty("h2.console.enabled",
                System.getenv().getOrDefault("H2_CONSOLE_ENABLED", "true")));
        String port = System.getProperty("h2.console.port",
                System.getenv().getOrDefault("H2_CONSOLE_PORT", "9092"));

        if (!enabled) {
            LOG.info("H2 Web Console disabled.");
            return;
        }

        try {
            webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", port).start();
            LOG.infof("H2 Web Console started at http://localhost:%s", port);
        } catch (Exception e) {
            LOG.error("Failed to start H2 Web Console", e);
        }
    }

    void onStop(@Observes ShutdownEvent ev) {
        if (webServer != null) {
            webServer.stop();
            LOG.info("H2 Web Console stopped.");
        }
    }
}
