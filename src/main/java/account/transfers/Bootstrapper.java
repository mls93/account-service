package account.transfers;

import account.transfers.config.AccountModule;
import account.transfers.config.database.DatabaseServer;
import account.transfers.servlets.AccountCreateServlet;
import account.transfers.servlets.AccountGetServlet;
import account.transfers.servlets.TransferServlet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;

public class Bootstrapper {
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new AccountModule(), new JpaPersistModule("item-manager"));
        DatabaseServer databaseServer = injector.getInstance(DatabaseServer.class);
        databaseServer.startDatabase();
        injector.getInstance(PersistService.class).start();
        startApplicationServer(injector);
    }

    private static void startApplicationServer(Injector injector) throws Exception {
        Server server = new Server(PORT);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
        addServlet(TransferServlet.class, "/transferMoney", injector, servletContextHandler);
        addServlet(AccountGetServlet.class, "/getBalance", injector, servletContextHandler);
        addServlet(AccountCreateServlet.class, "/create", injector, servletContextHandler);
        server.start();
        server.join();
    }

    private static <T extends Servlet> void addServlet(Class<T> clazz, String path, Injector injector, ServletContextHandler servletContextHandler) {
        ServletHolder servletHolder = new ServletHolder(injector.getInstance(clazz));
        servletContextHandler.addServlet(servletHolder, path);
    }
}
