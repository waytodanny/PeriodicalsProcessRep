package com.periodicals.dao.jdbc;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.dao.interfaces.RolesDao;
import com.periodicals.dao.entities.Role;
import com.periodicals.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RolesJdbcDao extends AbstractJdbcDao<Role, Byte> implements RolesDao {

    @Override
    protected String getSelectQuery() {
        return "SELECT id, name FROM roles ";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO roles (name) VALUES (?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE roles SET name = ? WHERE id = ?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM roles WHERE id = ?;";
    }

    @Override
    protected Byte getGeneratedKey(ResultSet rs) throws DaoException {
        try {
            if (rs.next())
                return rs.getByte(1);

            throw new SQLException("entry was not written in DB");
        } catch (SQLException e) {
            throw new DaoException("No keys were generated: " + e.getMessage());
        }
    }

    @Override
    protected void setGeneratedKey(Role role, Byte genId) throws IllegalArgumentException {
        role.setId(genId);
    }

    @Override
    protected List<Role> parseResultSet(ResultSet rs) throws DaoException {
        List<Role> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getByte("id"));
                role.setName(rs.getString("name"));

                result.add(role);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DaoException(e.getMessage());
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stmt, Role role) throws DaoException {
        try {
            stmt.setString(1, role.getName());
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stmt, Role role) throws DaoException {
        try {
            stmt.setString(1, role.getName());
            stmt.setLong(2, role.getId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Role getByName(String name) throws DaoException {
        if (Objects.isNull(name)) {
            log.error("Attempt to get user by nullable login");
            throw new DaoException("Attempt to get user by nullable login");
        }

        String sqlQuery = getSelectQuery() + " WHERE name = ?";

        log.debug("Trying to get role by name");
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            stmt.setObject(1, name);

            log.debug("Trying to execute select query");
            ResultSet rs = stmt.executeQuery();

            return parseResultSet(rs).iterator().next();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e.getMessage());
        }
    }
}
