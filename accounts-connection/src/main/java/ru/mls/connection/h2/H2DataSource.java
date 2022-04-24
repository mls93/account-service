package ru.mls.connection.h2;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbcx.JdbcDataSource;
import ru.mls.connection.ConnectionProvider;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ru.mls.connection.ConnectionConstants.TCP_PORT_VAR_NAME;

public class H2DataSource extends JdbcDataSource implements ConnectionProvider{
    @Inject
    public H2DataSource(
            @Named(TCP_PORT_VAR_NAME) int tcpPort,
            @Named("database.username") String username,
            @Named("database.password") String password,
            @Named("database.name") String database) {
        super();
        setURL(String.format("jdbc:h2:tcp://localhost:%s/~/%s", tcpPort, database));
        setPassword(password);
        setUser(username);
    }
}