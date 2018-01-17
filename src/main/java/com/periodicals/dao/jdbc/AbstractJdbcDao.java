package com.periodicals.dao.jdbc;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.dao.entities.util.Identified;
import com.periodicals.exceptions.DaoException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.periodicals.utils.JdbcQueriesHolder.COUNT_PARAM;

public abstract class AbstractJdbcDao<T extends Identified<K>, K> {
    private static final Logger LOGGER = Logger.getLogger(AbstractJdbcDao.class);

    protected abstract Object[] getInsertObjectParams(T object) throws DaoException;

    protected abstract Object[] getObjectUpdateParams(T object) throws DaoException;

    protected abstract K getGeneratedKey(ResultSet rs) throws SQLException;

    protected abstract List<T> parseResultSet(ResultSet rs) throws DaoException;

    K insert(String insertQuery, Object[] params) throws DaoException {
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return getGeneratedKey(rs);
            }
            return null;
        } catch (Exception e) {
            LOGGER.error("Can't insert into DB");
            throw new DaoException(e);
        }
    }

    List<T> selectObjects(String query, Object... params) throws DaoException {
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            ResultSet rs = stmt.executeQuery();
            return parseResultSet(rs);
        } catch (Exception e) {
            LOGGER.error("Can't get object DB");
            throw new DaoException(e);
        }
    }


    T selectObject(String query, Object... params) throws DaoException {
        List<T> objects = selectObjects(query, params);
        if(objects.size() > 0){
            return objects.get(0);
        }
        throw new DaoException("No object founded by given params");
    }


    void update(String query, Object... params) throws DaoException {
        executeUpdate(query, params);
    }

    void delete(String query, Object... params) throws DaoException {
        executeUpdate(query, params);
    }

    private void executeUpdate(String query, Object... params) throws DaoException {
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            stmt.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Cannot parse update query", e);
            throw new DaoException(e);
        }
    }

    Long getEntriesCount(String query, Object... params) throws DaoException {
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getLong(COUNT_PARAM);
            }
            throw new DaoException("failed to obtain count");
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}