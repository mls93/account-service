package account.transfers.config.database;

import lombok.RequiredArgsConstructor;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor
public class H2DatabaseServer implements DatabaseServer{
    private static final String TCP_PORT = "9094";
    private static final String WEB_PORT = "8082";
    private final String user;
    private final String password;
    private final String database;

    private Server webServer;
    private Server tcpServer;

    private volatile Exception serverInitException;

    @Override
    public void startDatabase(){
        new Thread(this::startWebServer).start();
        new Thread(this::startTcpServer).start();
        waitUntilServersStart();
    }

    private void startWebServer() {
        try {
            webServer = Server.createWebServer("-webAllowOthers", "-webPort", WEB_PORT);
            webServer.start();
        } catch (SQLException e) {
            serverInitException = e;
        }
    }

    private void startTcpServer() {
        try {
            tcpServer = Server.createTcpServer("-tcpAllowOthers", "-tcpPort", TCP_PORT);
            tcpServer.start();
        } catch (SQLException e) {
            serverInitException = e;
        }
    }

    private void waitUntilServersStart() {
        while (tcpServer == null || webServer == null) {
            if (serverInitException != null){
                throw new RuntimeException("Error when starting database server", serverInitException);
            }
            try {
                Thread.sleep(40);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
        }
    }

    public Connection getConnection() throws SQLException {
            String url = "jdbc:h2:" + tcpServer.getURL() + "/~/" + database;
        return DriverManager.getConnection(url, user, password);
    }
}