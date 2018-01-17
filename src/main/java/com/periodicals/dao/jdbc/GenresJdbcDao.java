package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.GenresDao;
import com.periodicals.entities.Genre;
import com.periodicals.exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.periodicals.utils.JdbcQueriesHolder.*;

public class GenresJdbcDao extends AbstractJdbcDao<Genre, Short> implements GenresDao {

    @Override
    public Genre getGenreByName(String name) throws DaoException {
        return super.selectObject(GENRE_SELECT_BY_NAME, name);
    }

    @Override
    public Short add(Genre element) throws DaoException {
        return super.insert(GENRE_INSERT, getInsertObjectParams(element));
    }

    @Override
    public Genre getById(Short id) throws DaoException {
        return super.selectObject(GENRE_SELECT_BY_ID, id);
    }

    @Override
    public void update(Genre object) throws DaoException {
        super.update(GENRE_UPDATE, getObjectUpdateParams(object));
    }

    @Override
    public void delete(Short id) throws DaoException {
        super.delete(GENRE_DELETE, id);
    }

    @Override
    public List<Genre> getAll() throws DaoException {
        return super.selectObjects(GENRE_SELECT_ALL);
    }

    @Override
    protected Object[] getInsertObjectParams(Genre object) throws DaoException {
        String name = object.getName();

        return new Object[]{name};
    }

    @Override
    protected Object[] getObjectUpdateParams(Genre object) throws DaoException {
        Short id = object.getId();
        String name = object.getName();

        return new Object[]{name, id};
    }

    @Override
    protected Short getGeneratedKey(ResultSet rs) throws SQLException {
        return rs.getShort(1);
    }

    @Override
    protected List<Genre> parseResultSet(ResultSet rs) throws DaoException {
        List<Genre> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Genre genre = new Genre();
                genre.setId(rs.getShort("id"));
                genre.setName(rs.getString("name"));

                result.add(genre);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DaoException(e);
        }
        return result;
    }
}
