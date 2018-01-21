package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.RolesDao;
import com.periodicals.entities.Role;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.periodicals.utils.ResourceHolders.JdbcQueriesHolder.*;

public class RolesJdbcDao extends AbstractJdbcDao<Role, String> implements RolesDao {
    private static final String ID = AttributesPropertyManager.getProperty("role.id");
    private static final String NAME = AttributesPropertyManager.getProperty("role.name");

    @Override
    public Role getById(String id) throws DaoException {
        return super.selectObject(ROLE_SELECT_BY_ID, id);
    }

    @Override
    public void add(Role role) throws DaoException {
        super.insert(ROLE_INSERT, getInsertObjectParams(role));
    }

    @Override
    public void update(Role role) throws DaoException {
        super.update(USER_UPDATE, getObjectUpdateParams(role));
    }

    @Override
    public void delete(String id) throws DaoException {
        super.delete(USER_DELETE, id);
    }

    @Override
    public List<Role> getAll() throws DaoException {
        return super.selectObjects(ROLE_SELECT_ALL);
    }

    @Override
    public Role getByName(String name) throws DaoException {
        return super.selectObject(ROLE_SELECT_BY_NAME, name);
    }

    @Override
    protected Object[] getInsertObjectParams(Role role) throws DaoException {
        return new Object[]{
                role.getId(),
                role.getName()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(Role role) throws DaoException {
        return new Object[]{
                role.getName(),
                role.getId()
        };
    }

    @Override
    protected List<Role> parseResultSet(ResultSet rs) throws DaoException {
        List<Role> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getString(ID));
                role.setName(rs.getString(NAME));

                result.add(role);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DaoException(e.getMessage());
        }
        return result;
    }

//    @Override
//    protected Byte getGeneratedKey(ResultSet rs) throws SQLException {
//        return rs.getByte(1);
//    }
}
