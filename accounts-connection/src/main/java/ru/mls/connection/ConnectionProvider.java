package ru.mls.connection;

import java.sql.Connection;

public interface ConnectionProvider {
    Connection getConnection();
}
