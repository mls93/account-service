package ru.mls;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseServer {
    void startDatabase();

    Connection getConnection() throws SQLException;
}
