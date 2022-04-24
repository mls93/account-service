package ru.mls.connection.h2;

import com.google.inject.AbstractModule;
import ru.mls.connection.ConnectionProvider;

import javax.sql.DataSource;

public class ConnectionProviderModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ConnectionProvider.class)
                .to(H2DataSource.class).asEagerSingleton();
        bind(DataSource.class)
                .to(H2DataSource.class).asEagerSingleton();
    }
}