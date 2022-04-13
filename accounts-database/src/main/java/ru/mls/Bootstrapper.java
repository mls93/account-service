package ru.mls;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ru.mls.server.DatabaseServerModule;

public class Bootstrapper {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new DatabaseServerModule());
        DatabaseServer databaseServer = injector.getInstance(DatabaseServer.class);
        databaseServer.startDatabase();
    }
}
