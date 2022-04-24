package ru.mls.server;

import org.h2.tools.Server;
import ru.mls.DatabaseServer;
import ru.mls.connection.ConnectionConstants;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.CompletableFuture.runAsync;

public class H2DatabaseServer implements DatabaseServer {
    private final int webPort;
    private final int tcpPort;

    @Inject
    public H2DatabaseServer(
            @Named(ConnectionConstants.TCP_PORT_VAR_NAME) int tcpPort,
            @Named("database.web.port") int webPort) {
        this.webPort = webPort;
        this.tcpPort = tcpPort;
    }

    @Override
    public void startDatabase() {
        CompletableFuture<Void> webServerFuture = runAsync(this::startWebServer);
        CompletableFuture<Void> tcpServerFuture = runAsync(this::startTcpServer);
        CompletableFuture.allOf(webServerFuture, tcpServerFuture).join();
        try {
            webServerFuture.get();
            tcpServerFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error when starting database server", e);
        }
    }

    private void startWebServer() {
        try {
            Server webServer = Server.createWebServer("-webAllowOthers", "-webPort", String.valueOf(webPort));
            webServer.start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void startTcpServer() {
        try {
            Server tcpServer = Server.createTcpServer("-tcpAllowOthers", "-tcpPort", String.valueOf(tcpPort));
            tcpServer.start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}