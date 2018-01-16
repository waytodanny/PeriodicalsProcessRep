package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.PublishersDao;
import com.periodicals.dao.entities.Publisher;
import com.periodicals.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PublishersJdbcDao extends AbstractJdbcDao<Publisher, Integer> implements PublishersDao {
    @Override
    public Integer add(Publisher element) throws DaoException {
        return null;
    }

    @Override
    public Publisher getById(Integer key) throws DaoException {
        return null;
    }

    @Override
    public void update(Publisher object) throws DaoException {

    }

    @Override
    public void delete(Integer key) throws DaoException {

    }

    @Override
    public List<Publisher> getAll() throws DaoException {
        return null;
    }

    @Override
    protected Object[] getObjectParams(Publisher object) throws DaoException {
        return new Object[0];
    }

    @Override
    protected Object[] getObjectUpdateParams(Publisher object) throws DaoException {
        return new Object[0];
    }

    @Override
    protected Integer getGeneratedKey(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected List<Publisher> parseResultSet(ResultSet rs) throws DaoException {
        return null;
    }

//    @Override
//    public String getSelectQuery() {
//        return "SELECT id, name FROM publishers ";
//    }
//
//    @Override
//    public String getInsertQuery() {
//        return "INSERT INTO publishers (name) VALUES (?);";
//    }
//
//    @Override
//    public String getUpdateQuery() {
//        return "UPDATE publishers SET name = ? WHERE id = ?;";
//    }
//
//    @Override
//    public String getDeleteQuery() {
//        return "DELETE FROM publishers WHERE id = ?;";
//    }
//
//    @Override
//    protected Object[] getObjectParams(Publisher object) throws DaoException {
//        return new Object[0];
//    }
//
//    @Override
//    protected Object[] getObjectUpdateParams(Publisher object) throws DaoException {
//        return new Object[0];
//    }
//
//    @Override
//    public Integer getGeneratedKey(ResultSet rs) throws DaoException {
//        try {
//            if (rs.next()){
//                return rs.getInt(1);
//            }
//            throw new SQLException("entry was not written in DB");
//        } catch (SQLException e) {
//            throw new DaoException("No keys were generated: " + e.getMessage());
//        }
//    }
//
//    @Override
//    protected void setGeneratedKey(Publisher object, Integer genId) {
//        object.setId(genId);
//    }
//
//    @Override
//    protected List<Publisher> parseResultSet(ResultSet rs) throws DaoException {
//        List<Publisher> result = new ArrayList<>();
//        try {
//            while (rs.next()) {
//                PersistPublisher publ = new PersistPublisher();
//                publ.setId(rs.getInt("id"));
//                publ.setName(rs.getString("name"));
//
//                result.add(publ);
//            }
//        } catch (Exception e) {
//            throw new DaoException(e);
//        }
//        return result;
//    }
//
//    @Override
//    protected void prepareStatementForInsert(PreparedStatement stmt, Publisher publ) throws DaoException {
//        try {
//            stmt.setString(1, publ.getName());
//        } catch (Exception e) {
//            throw new DaoException(e);
//        }
//    }
//
//    @Override
//    protected void prepareStatementForUpdate(PreparedStatement stmt, Publisher publ) throws DaoException {
//        try {
//            stmt.setString(1, publ.getName());
//            stmt.setInt(2, publ.getId());
//        } catch (Exception e) {
//            throw new DaoException(e);
//        }
//    }
}
