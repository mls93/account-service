package ru.mls.connection.h2;

import com.google.inject.AbstractModule;
import ru.mls.connection.ConnectionProvider;

public class ConnectionProviderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ConnectionProvider.class)
                .to(H2ConnectionProvider.class).asEagerSingleton();
    }
}