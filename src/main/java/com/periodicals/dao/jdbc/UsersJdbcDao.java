package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.UsersDao;
import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.periodicals.utils.ResourceHolders.JdbcQueriesHolder.*;

public class UsersJdbcDao extends AbstractJdbcDao<User, String> implements UsersDao {
    private static final String ID = AttributesPropertyManager.getProperty("user.id");
    private static final String NAME = AttributesPropertyManager.getProperty("user.name");
    private static final String PASSWORD = AttributesPropertyManager.getProperty("user.password");
    private static final String EMAIL = AttributesPropertyManager.getProperty("user.email");
    private static final String ROLE_ID = AttributesPropertyManager.getProperty("user.role_id");
    private static final String ROLE_NAME = AttributesPropertyManager.getProperty("user.role_name");

    @Override
    public User getById(String id) throws DaoException {
        return super.selectObject(USER_SELECT_BY_ID, id);
    }

    @Override
    public void add(User user) throws DaoException {
        super.insert(USER_INSERT, getInsertObjectParams(user));
    }

    @Override
    public void update(User user) throws DaoException {
        super.update(USER_UPDATE, getObjectUpdateParams(user));
    }

    @Override
    public void delete(String key) throws DaoException {
        super.delete(USER_DELETE, key);
    }

    @Override
    public List<User> getAll() throws DaoException {
        return super.selectObjects(USER_SELECT_ALL);
    }

    @Override
    public User getByLogin(String login) throws DaoException {
        return super.selectObject(USER_SELECT_BY_LOGIN, login);
    }

    @Override
    public int getUsersCount() throws DaoException {
        return super.getEntriesCount(USER_ENTRIES_COUNT);
    }

    @Override
    public List<User> geUsersLimited(int skip, int limit) throws DaoException {
        return super.selectObjects(USER_SELECT_LIMITED, skip, limit);
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
    protected List<User> parseResultSet(ResultSet rs) throws DaoException {
        List<User> result = new ArrayList<>();
        try {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString(ID));
                user.setLogin(rs.getString(NAME));
                user.setPassword(rs.getString(PASSWORD));
                user.setEmail(rs.getString(EMAIL));

                String roleId = rs.getString(ROLE_ID);
                String roleName = rs.getString(ROLE_NAME);

                user.setRole(new Role(roleId, roleName));

                result.add(user);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DaoException(e.getMessage());
        }
        return result;
    }

//    @Override
//    protected String getGeneratedKey(ResultSet rs) throws SQLException {
//        return rs.getString(1);
//    }
}
