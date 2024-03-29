package com.periodicals.dao.jdbc;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.interfaces.UsersDao;
import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.JdbcQueriesHolder.*;

public class UsersJdbcDao extends AbstractJdbcDao<User, UUID> implements UsersDao {
    private static final String ID = AttributesPropertyManager.getProperty("user.id");
    private static final String NAME = AttributesPropertyManager.getProperty("user.login");
    private static final String PASSWORD = AttributesPropertyManager.getProperty("user.password");
    private static final String EMAIL = AttributesPropertyManager.getProperty("user.email");
    private static final String ROLE_ID = AttributesPropertyManager.getProperty("user.role_id");

    private RolesJdbcDao rolesDao =
            (RolesJdbcDao) JdbcDaoFactory.getInstance().getRolesDao();


    @Override
    public User getEntityByPrimaryKey(UUID key) throws DaoException {
        return super.selectObject(USER_SELECT_BY_ID, key.toString());
    }

    @Override
    public void createEntity(User user) throws DaoException {
        super.insert(USER_INSERT, getInsertObjectParams(user));
    }

    @Override
    public void updateEntity(User user) throws DaoException {
        super.update(USER_UPDATE, getObjectUpdateParams(user));
    }

    @Override
    public void deleteEntity(User key) throws DaoException {
        super.delete(USER_DELETE, key.toString());
    }

    @Override
    public List<User> getEntityCollection() throws DaoException {
        return super.selectObjects(USER_SELECT_ALL);
    }

    @Override
    public List<User> getEntitiesListBounded(int skip, int limit) throws DaoException {
        return super.selectObjects(USER_SELECT_LIMITED, skip, limit);
    }

    @Override
    public int getEntitiesCount() throws DaoException {
        return super.getEntriesCount(USER_ENTRIES_COUNT);
    }

    @Override
    public User getUserByLogin(String login) throws DaoException {
        return super.selectObject(USER_SELECT_BY_LOGIN, login);
    }

    @Override
    protected Object[] getInsertObjectParams(User user) {
        return new Object[]{
                user.getId().toString(),
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getRole().getId().toString()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(User user) {
        return new Object[]{
                user.getLogin(),
                user.getPassword(),
                user.getEmail(),
                user.getRole().getId().toString(),
                user.getId().toString()
        };
    }

    @Override
    protected List<User> parseResultSet(ResultSet rs) throws DaoException {
        List<User> result = new ArrayList<>();
        try {
            while (rs.next()) {
                User user = new User();
                user.setId(UUID.fromString(rs.getString(ID)));
                user.setLogin(rs.getString(NAME));
                user.setPassword(rs.getString(PASSWORD));
                user.setEmail(rs.getString(EMAIL));

                Role role = rolesDao.getEntityByPrimaryKey(UUID.fromString(rs.getString(ROLE_ID)));
                user.setRole(role);

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
