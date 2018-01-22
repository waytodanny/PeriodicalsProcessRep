package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.RolesDao;
import com.periodicals.entities.Role;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.ResourceHolders.JdbcQueriesHolder.*;

public class RolesJdbcDao extends AbstractJdbcDao<Role, UUID> implements RolesDao {
    private static final String ID = AttributesPropertyManager.getProperty("role.id");
    private static final String NAME = AttributesPropertyManager.getProperty("role.name");

    @Override
    public Role getEntityByPrimaryKey(UUID key) throws DaoException {
        return super.selectObject(ROLE_SELECT_BY_ID, key.toString());
    }

    @Override
    public void createEntity(Role role) throws DaoException {
        super.insert(ROLE_INSERT, getInsertObjectParams(role));
    }

    @Override
    public void updateEntity(Role role) throws DaoException {
        super.update(USER_UPDATE, getObjectUpdateParams(role));
    }

    @Override
    public void deleteEntity(UUID key) throws DaoException {
        super.delete(USER_DELETE, key);
    }

    @Override
    public List<Role> getEntityCollection() throws DaoException {
        return super.selectObjects(ROLE_SELECT_ALL);
    }

    @Override
    public int getEntitiesCount() throws DaoException {
        return super.getEntriesCount(ROLE_ENTRIES_COUNT);
    }

    @Override
    public Role getByName(String name) throws DaoException {
        return super.selectObject(ROLE_SELECT_BY_NAME, name);
    }

    @Override
    protected Object[] getInsertObjectParams(Role role) throws DaoException {
        return new Object[]{
                role.getId().toString(),
                role.getName()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(Role role) throws DaoException {
        return new Object[]{
                role.getName(),
                role.getId().toString()
        };
    }

    @Override
    protected List<Role> parseResultSet(ResultSet rs) throws DaoException {
        List<Role> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Role role = new Role();
                role.setId(UUID.fromString(rs.getString(ID)));
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
