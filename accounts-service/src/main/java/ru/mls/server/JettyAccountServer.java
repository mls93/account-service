package ru.mls.server;

import org.eclipse.jetty.server.Server;
import ru.mls.AccountServer;

class JettyAccountServer implements AccountServer {
    private final static int PORT = 8080;

    @Override
    public void start() {
        Server server = new Server(PORT);
        try {
            server.start();
            server.join();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
