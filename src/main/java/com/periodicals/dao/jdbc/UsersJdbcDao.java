package com.periodicals.dao.jdbc;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.dao.interfaces.UsersDao;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsersJdbcDao extends AbstractJdbcDao<User, String> implements UsersDao {

    private static final String SELECT_USERS_COUNT =
            "SELECT COUNT(*) AS count FROM users;";
    private static final String SELECT_USERS_SUBLIST =
            "SELECT id, login, pass, email, role_id FROM users LIMIT ?, ?;";


    @Override
    public String getSelectQuery() {
        return "SELECT id, login, pass, email, role_id FROM users ";
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO users (id, login, pass, email, role_id) VALUES (?,?,?,?,?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE users SET login = ?, pass = ?, email = ?, role_id = ? WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM users WHERE id = ?;";
    }

    @Override
    public String getGeneratedKey(ResultSet rs) throws DaoException {
        try {
            if (rs.next())
                return rs.getString(1);

            throw new SQLException("entry was not written in DB");
        } catch (SQLException e) {
            throw new DaoException("No keys were generated: " + e.getMessage());
        }
    }

    @Override
    protected void setGeneratedKey(User user, String genUuid) throws IllegalArgumentException {
        user.setId(genUuid);
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws DaoException {
        List<User> result = new ArrayList<>();
        try {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("pass"));
                user.setEmail(rs.getString("email"));
                user.setRoleId(rs.getByte("role_id"));

                result.add(user);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DaoException(e.getMessage());
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stmt, User user) throws DaoException {
        try {
            stmt.setString(1, user.getId());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getEmail());
            stmt.setByte(5, user.getRoleId());
        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stmt, User user) throws DaoException {
        try {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setByte(4, user.getRoleId());
            stmt.setString(5, user.getId());
        } catch (Exception e) {
            throw new DaoException(e.getMessage());
        }
    }

    @Override
    public User getUserByLogin(String login) throws DaoException {
        if (Objects.isNull(login)) {
            log.error("Attempt to get user by nullable login");
            throw new DaoException("Attempt to get user by nullable login");
        }

        User result;
        String sqlQuery = getSelectQuery() + " WHERE login = ?";

        log.debug("Trying to get user by login");
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setObject(1, login);

            log.debug("Trying to execute select query");
            ResultSet rs = stmt.executeQuery();
            List<User> founded = parseResultSet(rs);

            if (founded == null || founded.size() == 0) {
                log.debug("No objects by given login were founded");
                throw new DaoException("No objects by given login were founded");
            }

            log.debug("Object by given login was successfully founded");
            return founded.iterator().next();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e.getMessage());
        }
    }

    public int getUsersCount() throws DaoException {
        int result = 0;
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(SELECT_USERS_COUNT)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("count");
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    public List<User> getUsersSublist(int skip, int take) throws DaoException {
        if (skip < 0 && take < 0) {
            log.error("skip and take params must be > 0");
            throw new DaoException("skip and take params must be > 0");
        }

        List<User> result;
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(SELECT_USERS_SUBLIST)) {
            ;
            stmt.setInt(1, skip);
            stmt.setInt(2, take);

            ResultSet rs = stmt.executeQuery();
            result = parseResultSet(rs);

            log.debug("Successful object set parsing!");
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }

//    private class PersistUser extends User {
//        public void setId(int id) {
//            super.setId(id);
//        }
//    }
}
