package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.GenresDao;
import com.periodicals.entities.Genre;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.ResourceHolders.JdbcQueriesHolder.*;

public class GenresJdbcDao extends AbstractJdbcDao<Genre, UUID> implements GenresDao {
    private static final String ID = AttributesPropertyManager.getProperty("genre.id");
    private static final String NAME = AttributesPropertyManager.getProperty("genre.name");

    @Override
    public void createEntity(Genre entity) throws DaoException {
        super.insert(GENRE_INSERT, getInsertObjectParams(entity));
    }

    @Override
    public void updateEntity(Genre entity) throws DaoException {
        super.update(GENRE_UPDATE, getObjectUpdateParams(entity));
    }

    @Override
    public void deleteEntity(UUID key) throws DaoException {
        super.delete(GENRE_DELETE, key.toString());
    }

    @Override
    public Genre getEntityByPrimaryKey(UUID key) throws DaoException {
        return super.selectObject(GENRE_SELECT_BY_ID, key.toString());
    }

    @Override
    public List<Genre> getEntityCollection() throws DaoException {
        return super.selectObjects(GENRE_SELECT_ALL);
    }

    @Override
    public int getEntitiesCount() throws DaoException {
        return super.getEntriesCount(GENRE_ENTRIES_COUNT);
    }

    @Override
    public Genre getGenreByName(String name) throws DaoException {
        return super.selectObject(GENRE_SELECT_BY_NAME, name);
    }

    @Override
    protected Object[] getInsertObjectParams(Genre genre) {
        return new Object[]{
                genre.getId().toString(),
                genre.getName()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(Genre genre) {
        return new Object[]{
                genre.getName(),
                genre.getId().toString()
        };
    }

    @Override
    protected List<Genre> parseResultSet(ResultSet rs) throws DaoException {
        List<Genre> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(UUID.fromString(rs.getString(ID)));
                genre.setName(rs.getString(NAME));

                result.add(genre);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DaoException(e);
        }
        return result;
    }

//    @Override
//    protected Short getGeneratedKey(ResultSet rs) throws SQLException {
//        return rs.getShort(1);
//    }
}
