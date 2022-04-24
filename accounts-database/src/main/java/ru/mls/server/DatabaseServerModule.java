package ru.mls.server;

import com.google.inject.AbstractModule;
import ru.mls.DatabaseServer;

public class DatabaseServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DatabaseServer.class)
                .to(H2DatabaseServer.class).asEagerSingleton();
    }
}