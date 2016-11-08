package ru.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Леха
 * 07.11.2016.
 */
public interface ConnectionFactory {
    Connection getConnection() throws SQLException;
}
