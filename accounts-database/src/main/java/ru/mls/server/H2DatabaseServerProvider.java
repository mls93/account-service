package ru.mls.server;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

public class H2DatabaseServerProvider implements Provider<H2DatabaseServer> {
    private static H2DatabaseServer h2DatabaseServerInstance = null;

    @Inject
    public H2DatabaseServerProvider(
            @Named("database.web.port") int webPort,
            @Named("database.tcp.port") int tcpPort,
            @Named("database.username") String username,
            @Named("database.password") String password,
            @Named("database.name") String database) {
        if (h2DatabaseServerInstance == null) {
            h2DatabaseServerInstance = new H2DatabaseServer(webPort, tcpPort, username, password, database);
        }
    }

    @Override
    public H2DatabaseServer get() {
        return h2DatabaseServerInstance;
    }
}
