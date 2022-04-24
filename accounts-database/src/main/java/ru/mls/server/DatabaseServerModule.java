package ru.mls.server;

import com.google.inject.AbstractModule;
import ru.mls.ConnectionProvider;
import ru.mls.DatabaseServer;

public class DatabaseServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DatabaseServer.class)
                .toProvider(H2DatabaseServerProvider.class);
        bind(ConnectionProvider.class)
                .toProvider(H2DatabaseServerProvider.class);
    }
}