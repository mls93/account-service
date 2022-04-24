package ru.mls.connection.h2;

import ru.mls.connection.ConnectionProvider;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ru.mls.connection.ConnectionConstants.TCP_PORT_VAR_NAME;

public class H2ConnectionProvider implements ConnectionProvider {
    private final int tcpPort;
    private final String username;
    private final String password;
    private final String database;

    @Inject
    public H2ConnectionProvider(
            @Named(TCP_PORT_VAR_NAME) int tcpPort,
            @Named("database.username") String username,
            @Named("database.password") String password,
            @Named("database.name") String database) {
        this.tcpPort = tcpPort;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public Connection getConnection() {
        String url = String.format("jdbc:h2:tcp://localhost:%s/~/%s", tcpPort, database);
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}