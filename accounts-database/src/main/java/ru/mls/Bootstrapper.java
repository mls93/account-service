package ru.mls;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import ru.mls.connection.h2.ConnectionProviderModule;
import ru.mls.migration.MigrationProcessorModule;
import ru.mls.properties.PropertiesModule;
import ru.mls.server.DatabaseServerModule;

public class Bootstrapper {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(getModules());
        startDatabaseServer(injector);
        runMigrations(injector);
    }

    private static Module[] getModules() {
        return new Module[]{
                new PropertiesModule(),
                new DatabaseServerModule(),
                new MigrationProcessorModule(),
                new ConnectionProviderModule()
        };
    }

    private static void startDatabaseServer(Injector injector) {
        DatabaseServer databaseServer = injector.getInstance(DatabaseServer.class);
        databaseServer.startDatabase();
    }

    private static void runMigrations(Injector injector) {
        MigrationProcessor migrationProcessor = injector.getInstance(MigrationProcessor.class);
        migrationProcessor.runMigrations();
    }
}
