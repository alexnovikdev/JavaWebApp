package ru.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Алексей on 12.11.2016.
 */
public interface SqlTransaction<T> {
    T execute(Connection conn) throws SQLException;
}
