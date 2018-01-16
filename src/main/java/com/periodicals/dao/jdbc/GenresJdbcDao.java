package com.periodicals.dao.jdbc;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.dao.interfaces.GenresDao;
import com.periodicals.dao.entities.Genre;
import com.periodicals.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.periodicals.utils.JdbcQueriesHolder.*;

public class GenresJdbcDao extends AbstractJdbcDao<Genre, Short> implements GenresDao {
    public static final String SELECT_GENRE_BY_NAME = "SELECT id, name FROM genres WHERE name = ? LIMIT 0,1;";

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

//    @Override
//    public String getSelectQuery() {
//        return "SELECT id, name FROM genres ";
//    }
//
//    @Override
//    public String getInsertQuery() {
//        return "INSERT INTO genres(name) VALUES (?);";
//    }
//
//    @Override
//    public String getUpdateQuery() {
//        return "UPDATE genres SET name = ? WHERE id = ?;";
//    }
//
//    @Override
//    public String getDeleteQuery() {
//        return "DELETE FROM genres WHERE id = ?;";
//    }
//
//    @Override
//    public Short getGeneratedKey(ResultSet rs) throws DaoException {
//        try {
//            if (rs.next())
//                return rs.getShort(1);
//
//            throw new SQLException("entry was not written in DB");
//        } catch (SQLException e) {
//            throw new DaoException("No keys were generated: " + e.getMessage());
//        }
//    }
//
//    @Override
//    protected void setGeneratedKey(Genre genre, Short genId) throws IllegalArgumentException {
//        genre.setId(genId);
//    }
//
//    @Override
//    protected List<Genre> parseResultSet(ResultSet rs) throws DaoException {
//        List<Genre> result = new ArrayList<>();
//        try {
//            while (rs.next()) {
//                Genre genre = new Genre();
//                genre.setId(rs.getShort("id"));
//                genre.setName(rs.getString("name"));
//
//                result.add(genre);
//            }
//        } catch (SQLException | IllegalArgumentException e) {
//            throw new DaoException(e);
//        }
//        return result;
//    }
//
//    @Override
//    protected void prepareStatementForInsert(PreparedStatement stmt, Genre genre) throws DaoException {
//        try {
//            stmt.setString(1, genre.getName());
//        } catch (SQLException e) {
//            throw new DaoException(e);
//        }
//    }
//
//    @Override
//    protected void prepareStatementForUpdate(PreparedStatement stmt, Genre genre) throws DaoException {
//        try {
//            stmt.setString(1, genre.getName());
//            stmt.setLong(2, genre.getId());
//        } catch (SQLException e) {
//            throw new DaoException(e);
//        }
//    }
//
//    @Override
//    public Genre getGenreByName(String name) throws DaoException {
//        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
//             PreparedStatement stmt = conn.prepareStatement(SELECT_GENRE_BY_NAME)) {
//
//            stmt.setString(1, name);
//            ResultSet rs = stmt.executeQuery();
//
//            return parseResultSet(rs).iterator().next();
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new DaoException(e);
//        }
//    }

}
