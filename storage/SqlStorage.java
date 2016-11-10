package ru.webapp.storage;

import ru.webapp.WebAppException;
import ru.webapp.model.Resume;
import ru.webapp.sql.ConnectionFactory;
import ru.webapp.sql.Sql;
import ru.webapp.sql.SqlExecutor;

import java.sql.*;
import java.util.Collection;

/**
 * Леха
 * 07.11.2016.
 */
public class SqlStorage implements IStorage {
    public Sql sql;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sql = new Sql(new ConnectionFactory() {
            @Override
            public Connection getConnection() throws SQLException {
                return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            }
        });
    }

    @Override
    public void clear() {
        sql.execute("DELETE FROM resume");
    }

    @Override
    public void save(final Resume resume) {
        sql.execute("INSERT INTO resume (uuid, full_name, location, home_page) VALUES (?,?,?,?)", new SqlExecutor<Void>() {
            @Override
            public Void execute(PreparedStatement st) throws SQLException {
                st.setString(1, resume.getUuid());
                st.setString(2, resume.getFullName());
                st.setString(3, resume.getLocation());
                st.setString(4, resume.getHomePage());
                st.execute();
                return null;
            }
        });
    }

    @Override
    public void update(Resume resume) {
        sql.execute("UPDATE resume SET full_name=?, location=?, home_page=? WHERE uuid=?", new SqlExecutor<Void>() {
            @Override
            public Void execute(PreparedStatement st) throws SQLException {
                st.setString(1, resume.getFullName());
                st.setString(2, resume.getLocation());
                st.setString(3, resume.getHomePage());
                st.setString(4, resume.getUuid());
                if (st.executeUpdate() == 0) {
                    throw new WebAppException("Resume not found", resume);
                }
                return null;
            }
        });
    }

    @Override
    public Resume load(final String uuid) {
        return sql.execute("SELECT * FROM resume WHERE uuid=?", new SqlExecutor<Resume>() {
            @Override
            public Resume execute(PreparedStatement st) throws SQLException {
                st.setString(1, uuid);
                ResultSet rs = st.executeQuery();
                // resultset close where preparedstatemetn close in postgreSQL, but in Mysql ?
                if (!rs.next()) {
                    throw new WebAppException("Resume " + uuid + " is not exist");
                }
                Resume r = new Resume(uuid, rs.getString("full_name"), rs.getString("location"), rs.getString("home_page"));
                return r;
            }
        });
    }

    @Override
    public void delete(String uuid) {
        sql.execute("DELETE FROM resume WHERE uuid=?", new SqlExecutor<Void>() {
            @Override
            public Void execute(PreparedStatement st) throws SQLException {
                st.setString(1, uuid);
                if (st.executeUpdate() == 0) {
                    throw new WebAppException("Resume " + uuid + " not exist", uuid);
                }
                return null;
            }
        });
    }

    @Override
    public Collection<Resume> getAllSorted() {
        //TODO implement collection
        return null;
    }

    @Override
    public int size() {
        return sql.execute("SELECT count(*) FROM resume", new SqlExecutor<Integer>() {
            @Override
            public Integer execute(PreparedStatement st) throws SQLException {
                ResultSet rs = st.executeQuery();
                rs.next();
                return rs.getInt(1);
            }
        });
    }

    @Override
    public boolean isSectionSupported() {
        return false;
    }
}
