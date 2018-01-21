package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.PublishersDao;
import com.periodicals.entities.Publisher;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static com.periodicals.utils.ResourceHolders.JdbcQueriesHolder.*;

public class PublishersJdbcDao extends AbstractJdbcDao<Publisher, String> implements PublishersDao {
    private static final String ID = AttributesPropertyManager.getProperty("publisher.id");
    private static final String NAME = AttributesPropertyManager.getProperty("publisher.name");

    @Override
    public void add(Publisher publisher) throws DaoException {
        super.insert(PUBLISHER_INSERT, getInsertObjectParams(publisher));
    }

    @Override
    public Publisher getById(String id) throws DaoException {
        return super.selectObject(PUBLISHER_SELECT_BY_ID, id);
    }

    @Override
    public void update(Publisher object) throws DaoException {
        super.update(PUBLISHER_UPDATE, getObjectUpdateParams(object));
    }

    @Override
    public void delete(String id) throws DaoException {
        super.delete(PUBLISHER_DELETE, id);
    }

    @Override
    public List<Publisher> getAll() throws DaoException {
        return super.selectObjects(PUBLISHER_SELECT_ALL);
    }

    @Override
    protected Object[] getInsertObjectParams(Publisher publisher) {
        return new Object[]{
                publisher.getId(),
                publisher.getName()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(Publisher publisher) {
        return new Object[]{
                publisher.getName(),
                publisher.getId()
        };
    }

    @Override
    protected List<Publisher> parseResultSet(ResultSet rs) throws DaoException {
        List<Publisher> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Publisher publisher = new Publisher();
                publisher.setId(rs.getString(ID));
                publisher.setName(rs.getString(NAME));

                result.add(publisher);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }

//    @Override
//    protected Integer getGeneratedKey(ResultSet rs) throws SQLException {
//        return rs.getInt(1);
//    }
}
