package ru.webapp.storage;

import ru.webapp.WebAppException;
import ru.webapp.model.ContactType;
import ru.webapp.model.Resume;
import ru.webapp.sql.ConnectionFactory;
import ru.webapp.sql.Sql;
import ru.webapp.sql.SqlExecutor;
import ru.webapp.sql.SqlTransaction;
import ru.webapp.util.Util;

import java.sql.*;
import java.util.*;

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
        sql.execute(new SqlTransaction<Void>() {
            @Override
            public Void execute(Connection conn) throws SQLException {
                try (PreparedStatement st = conn.prepareStatement("INSERT INTO resume (uuid, full_name, location, home_page) VALUES (?,?,?,?)")) {
                    st.setString(1, resume.getUuid());
                    st.setString(2, resume.getFullName());
                    st.setString(3, resume.getLocation());
                    st.setString(4, resume.getHomePage());
                    st.execute();
                }
                insertContact(conn, resume);
                return null;
            }
        });
    }

    @Override
    public void update(Resume resume) {
        sql.execute(new SqlTransaction<Void>() {
            @Override
            public Void execute(Connection conn) throws SQLException {
                try (PreparedStatement st = conn.prepareStatement("UPDATE resume SET full_name=?, location=?, home_page=? WHERE uuid=?")) {
                    st.setString(1, resume.getFullName());
                    st.setString(2, resume.getLocation());
                    st.setString(3, resume.getHomePage());
                    st.setString(4, resume.getUuid());
                    if (st.executeUpdate() == 0) {
                        throw new WebAppException("Resume not found", resume);
                    }
                }
                deleteContacts(conn, resume);
                insertContact(conn, resume);
                return null;
            }
        });
    }

    @Override
    public Resume load(final String uuid) {
        return sql.execute("SELECT * FROM resume LEFT JOIN contact ON resume.uuid = contact.resume_uuid WHERE uuid=?", new SqlExecutor<Resume>() {
            @Override
            public Resume execute(PreparedStatement st) throws SQLException {
                st.setString(1, uuid);
                ResultSet rs = st.executeQuery();
                // resultset close where preparedstatemetn close in postgreSQL, but in Mysql ?
                if (!rs.next()) {
                    throw new WebAppException("Resume " + uuid + " is not exist");
                }
                Resume r = new Resume(uuid, rs.getString("full_name"), rs.getString("location"), rs.getString("home_page"));
                addContact(rs, r);
                while (rs.next()) {
                    addContact(rs, r);
                }
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
        return sql.execute("SELECT * FROM resume LEFT JOIN contact ON resume.uuid = contact.resume_uuid ORDER BY full_name", new SqlExecutor<Collection<Resume>>() {
            @Override
            public Collection<Resume> execute(PreparedStatement st) throws SQLException {
                ResultSet rs = st.executeQuery();
                Map<String, Resume> map = new LinkedHashMap<>();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = map.get(uuid);
                    if (resume == null) {
                        resume = new Resume(uuid, rs.getString("full_name"),
                                rs.getString("location"), rs.getString("home_page"));
                        map.put(uuid, resume);
                    }
                    addContact(rs, resume);
                }
                return map.values();
            }
        });
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

    private void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {
                st.setString(1, r.getUuid());
                st.setString(2, entry.getKey().name());
                st.setString(3, entry.getValue());
                st.addBatch();
            }
            st.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        try(PreparedStatement st = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
            st.setString(1, r.getUuid());
            st.execute();
        }
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (!Util.isEmpty(value)) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.addContact(type, value);
        }
    }
}


