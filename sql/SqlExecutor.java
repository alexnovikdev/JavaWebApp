package ru.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Леха
 * 08.11.2016.
 */
public interface SqlExecutor<T> {
    T execute(PreparedStatement st) throws SQLException;
}
