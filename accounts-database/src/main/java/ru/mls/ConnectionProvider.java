package ru.mls;

import java.sql.Connection;

public interface ConnectionProvider {
    Connection getConnection();
}
