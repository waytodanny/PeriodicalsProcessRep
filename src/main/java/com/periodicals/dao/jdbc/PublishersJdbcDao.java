package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.PublishersDao;
import com.periodicals.entities.Publisher;
import com.periodicals.exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.periodicals.utils.JdbcQueriesHolder.*;

public class PublishersJdbcDao extends AbstractJdbcDao<Publisher, Integer> implements PublishersDao {

    @Override
    public Integer add(Publisher publisher) throws DaoException {
        return super.insert(PUBLISHER_INSERT, getInsertObjectParams(publisher));
    }

    @Override
    public Publisher getById(Integer id) throws DaoException {
        return super.selectObject(PUBLISHER_SELECT_BY_ID, id);
    }

    @Override
    public void update(Publisher object) throws DaoException {
        super.update(PUBLISHER_UPDATE, getObjectUpdateParams(object));
    }

    @Override
    public void delete(Integer key) throws DaoException {
        super.delete(PUBLISHER_DELETE, key);
    }

    @Override
    public List<Publisher> getAll() throws DaoException {
        return super.selectObjects(PUBLISHER_SELECT_ALL);
    }

    @Override
    protected Object[] getInsertObjectParams(Publisher object) throws DaoException {
        String name = object.getName();

        return new Object[]{name};
    }

    @Override
    protected Object[] getObjectUpdateParams(Publisher object) throws DaoException {
        Integer id = object.getId();
        String name = object.getName();

        return new Object[]{name, id};
    }

    @Override
    protected Integer getGeneratedKey(ResultSet rs) throws SQLException {
        return rs.getInt(1);
    }

    @Override
    protected List<Publisher> parseResultSet(ResultSet rs) throws DaoException {
        List<Publisher> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Publisher publ = new Publisher();
                publ.setId(rs.getInt("id"));
                publ.setName(rs.getString("name"));

                result.add(publ);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }
}
