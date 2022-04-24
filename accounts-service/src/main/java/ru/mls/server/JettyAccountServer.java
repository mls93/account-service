package ru.mls.server;

import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import ru.mls.AccountServer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.DispatcherType;

import static java.util.EnumSet.allOf;

class JettyAccountServer implements AccountServer {
    private final int port;

    @Inject
    public JettyAccountServer(@Named("jetty.server.port") int port) {
        this.port = port;
    }

    @Override
    public void start() {
        Server server = new Server(port);
        ServletContextHandler handler =
                new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        handler.addFilter(GuiceFilter.class, "/*", allOf(DispatcherType.class));
        handler.addServlet(DefaultServlet.class, "/");
        try {
            server.start();
            server.join();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
