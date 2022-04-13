package ru.mls.server;

import ru.mls.DatabaseServer;
import ru.mls.server.H2DatabaseServer;

import javax.inject.Provider;

public class DatabaseServerProvider implements Provider<DatabaseServer> {

    @Override
    public DatabaseServer get() {
        return new H2DatabaseServer("sa", "", "accounts");
    }
}
