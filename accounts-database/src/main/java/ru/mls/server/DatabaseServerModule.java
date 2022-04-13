package ru.mls.server;

import com.google.inject.AbstractModule;
import ru.mls.DatabaseServer;
import ru.mls.server.DatabaseServerProvider;

public class DatabaseServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DatabaseServer.class)
                .toProvider(DatabaseServerProvider.class);
    }
}