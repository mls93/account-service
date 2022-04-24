package ru.mls.server;

import org.h2.tools.Server;
import ru.mls.ConnectionProvider;
import ru.mls.DatabaseServer;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2DatabaseServer implements DatabaseServer, ConnectionProvider {
    private final int webPort;
    private final int tcpPort;
    private final String username;
    private final String password;
    private final String database;

    @Inject
    public H2DatabaseServer(
            @Named("database.web.port") int webPort,
            @Named("database.tcp.port") int tcpPort,
            @Named("database.username") String username,
            @Named("database.password") String password,
            @Named("database.name") String database) {
        this.webPort = webPort;
        this.tcpPort = tcpPort;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    private Server webServer;
    private Server tcpServer;

    private volatile Exception serverInitException;

    @Override
    public void startDatabase() {
        new Thread(this::startWebServer).start();
        new Thread(this::startTcpServer).start();
        waitUntilServersStart();
    }

    private void startWebServer() {
        try {
            webServer = Server.createWebServer("-webAllowOthers", "-webPort", String.valueOf(webPort));
            webServer.start();
        } catch (SQLException e) {
            serverInitException = e;
        }
    }

    private void startTcpServer() {
        try {
            tcpServer = Server.createTcpServer("-tcpAllowOthers", "-tcpPort", String.valueOf(tcpPort));
            tcpServer.start();
        } catch (SQLException e) {
            serverInitException = e;
        }
    }

    private void waitUntilServersStart() {
        while (tcpServer == null || webServer == null) {
            if (serverInitException != null) {
                throw new RuntimeException("Error when starting database server", serverInitException);
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Connection getConnection() {
        String url = "jdbc:h2:" + tcpServer.getURL() + "/~/" + database;
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}