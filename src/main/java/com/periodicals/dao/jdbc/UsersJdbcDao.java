package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.UsersDao;
import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.periodicals.utils.ResourceHolders.JdbcQueriesHolder.*;

public class UsersJdbcDao extends AbstractJdbcDao<User, String> implements UsersDao {

    @Override
    public User getByLogin(String login) throws DaoException {
        return super.selectObject(USER_SELECT_BY_LOGIN, login);
    }

    @Override
    public long getUsersCount() throws DaoException {
        return super.getEntriesCount(USER_ENTRIES_COUNT);
    }

    @Override
    public List<User> geUsersLimited(int skip, int take) throws DaoException {
        return super.selectObjects(USER_SELECT_SUBLIST, skip, take);
    }

    @Override
    public String add(User user) throws DaoException {
        return super.insert(USER_INSERT, getInsertObjectParams(user));
    }

    @Override
    public User getById(String id) throws DaoException {
        return super.selectObject(USER_SELECT_BY_ID, id);
    }

    @Override
    public void update(User user) throws DaoException {
        super.update(USER_UPDATE, getObjectUpdateParams(user));
    }

    @Override
    public void delete(User user) throws DaoException {
        super.delete(USER_DELETE, user.getId());
    }

    @Override
    public List<User> getAll() throws DaoException {
        return super.selectObjects(USER_SELECT_ALL);
    }

    @Override
    protected Object[] getInsertObjectParams(User user) {
        return new Object[]{
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getRole().getId()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(User user) {
        return new Object[]{
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getRole().getId(),
                user.getId()
        };
    }

    @Override
    protected String getGeneratedKey(ResultSet rs) throws SQLException {
        return rs.getString(1);
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

                Byte roleId = rs.getByte("role_id");
                String roleName = rs.getString("role_name");
                Role role = new Role(roleId, roleName);

                user.setRole(role);

                result.add(user);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DaoException(e.getMessage());
        }
        return result;
    }
}
