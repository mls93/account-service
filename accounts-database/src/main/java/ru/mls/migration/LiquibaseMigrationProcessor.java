package ru.mls.migration;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import ru.mls.connection.ConnectionProvider;
import ru.mls.MigrationProcessor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class LiquibaseMigrationProcessor implements MigrationProcessor {
    private final ConnectionProvider connectionProvider;
    private final String changeLogFile;

    @Inject
    public LiquibaseMigrationProcessor(ConnectionProvider connectionProvider,
                                       @Named("liquibase.changelog.file") String changeLogFile) {
        this.connectionProvider = connectionProvider;
        this.changeLogFile = changeLogFile;
    }

    @Override
    public void runMigrations() {
        try (Connection connection = connectionProvider.getConnection()) {
            Database database = getCorrectDatabaseImplementation(connection);
            try (Liquibase liquibase = new Liquibase(changeLogFile, new ClassLoaderResourceAccessor(), database)) {
                liquibase.update(new Contexts(), new LabelExpression());
            } catch (LiquibaseException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables);
        }
    }

    private Database getCorrectDatabaseImplementation(Connection connection) {
        try {
            return DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }
}
