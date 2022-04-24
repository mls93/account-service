package ru.mls.server;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Server;
import ru.mls.ConnectionProvider;
import ru.mls.DatabaseServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@RequiredArgsConstructor
public class H2DatabaseServer implements DatabaseServer, ConnectionProvider {
    private final int webPort;
    private final int tcpPort;
    private final String username;
    private final String password;
    private final String database;

    private Server tcpServer;

    @Override
    public void startDatabase() {
        CompletableFuture<Server> webServerFuture = supplyAsync(this::startWebServer);
        CompletableFuture<Server> tcpServerFuture = supplyAsync(this::startTcpServer);
        CompletableFuture.allOf(webServerFuture, tcpServerFuture).join();
        try {
            webServerFuture.get();
            this.tcpServer = tcpServerFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error when starting database server", e);
        }
    }

    private Server startWebServer() {
        try {
            Server webServer = Server.createWebServer("-webAllowOthers", "-webPort", String.valueOf(webPort));
            return webServer.start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Server startTcpServer() {
        try {
            Server tcpServer = Server.createTcpServer("-tcpAllowOthers", "-tcpPort", String.valueOf(tcpPort));
            return tcpServer.start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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