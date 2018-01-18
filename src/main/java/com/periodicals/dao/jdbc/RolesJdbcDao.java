package com.periodicals.dao.jdbc;

import com.periodicals.entities.Role;
import com.periodicals.dao.interfaces.RolesDao;
import com.periodicals.exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;import static com.periodicals.utils.ResourceHolders.JdbcQueriesHolder.*;

public class RolesJdbcDao extends AbstractJdbcDao<Role, Byte> implements RolesDao {

    @Override
    public Role getById(Byte id) throws DaoException {
        return super.selectObject(ROLE_SELECT_BY_NAME, id);
    }

    @Override
    public Byte add(Role role) throws DaoException {
        return super.insert(ROLE_INSERT, getInsertObjectParams(role));
    }

    @Override
    public void update(Role role) throws DaoException {
        super.update(USER_UPDATE, getObjectUpdateParams(role));
    }

    @Override
    public void delete(Role role) throws DaoException {
        super.delete(USER_DELETE, role.getId());
    }

    @Override
    public Role getByName(String name) throws DaoException {
        return super.selectObject(ROLE_SELECT_BY_NAME, name);
    }

    @Override
    public List<Role> getAll() throws DaoException {
        return super.selectObjects(ROLE_SELECT_ALL);
    }

    @Override
    protected Object[] getInsertObjectParams(Role role) throws DaoException {
        String name = role.getName();

        return new Object[]{name};
    }

    @Override
    protected Object[] getObjectUpdateParams(Role role) throws DaoException {
        Byte id = role.getId();
        String name = role.getName();

        return new Object[]{name, id};
    }

    @Override
    protected Byte getGeneratedKey(ResultSet rs) throws SQLException {
        return rs.getByte(1);
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
}
