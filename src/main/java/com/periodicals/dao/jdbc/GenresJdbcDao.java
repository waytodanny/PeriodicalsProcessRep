package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.GenresDao;
import com.periodicals.entities.Genre;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.periodicals.utils.ResourceHolders.JdbcQueriesHolder.*;

public class GenresJdbcDao extends AbstractJdbcDao<Genre, String> implements GenresDao {
    private static final String ID = AttributesPropertyManager.getProperty("genre.id");
    private static final String NAME = AttributesPropertyManager.getProperty("genre.name");

    @Override
    public void add(Genre element) throws DaoException {
        super.insert(GENRE_INSERT, getInsertObjectParams(element));
    }

    @Override
    public void update(Genre object) throws DaoException {
        super.update(GENRE_UPDATE, getObjectUpdateParams(object));
    }

    @Override
    public void delete(String key) throws DaoException {
        super.delete(GENRE_DELETE, key);
    }

    @Override
    public Genre getById(String id) throws DaoException {
        return super.selectObject(GENRE_SELECT_BY_ID, id);
    }

    @Override
    public List<Genre> getAll() throws DaoException {
        return super.selectObjects(GENRE_SELECT_ALL);
    }

    @Override
    public Genre getGenreByName(String name) throws DaoException {
        return super.selectObject(GENRE_SELECT_BY_NAME, name);
    }

    @Override
    protected Object[] getInsertObjectParams(Genre genre) {
        String id = genre.getId();
        String name = genre.getName();

        return new Object[]{id, name};
    }

    @Override
    protected Object[] getObjectUpdateParams(Genre genre) {
        String id = genre.getId();
        String name = genre.getName();

        return new Object[]{name, id};
    }

    @Override
    protected List<Genre> parseResultSet(ResultSet rs) throws DaoException {
        List<Genre> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getString(ID));
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
