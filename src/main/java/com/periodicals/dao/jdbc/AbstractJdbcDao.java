package com.periodicals.dao.jdbc;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.dao.interfaces.GenericDao;
import com.periodicals.dao.entities.util.Identified;
import com.periodicals.exceptions.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import javax.naming.OperationNotSupportedException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

/**
 * Created by Daniel Volnitsky on 23.12.17
 * Abstract class with basic CRUD and getAll operations using JDBC,
 * imposes an obligation for entity to have number type of key
 *
 * @param <T> object type which implementer dao working with
 * @param <K> object primary key type
 * @see GenericDao
 * @see DaoException
 */
public abstract class AbstractJdbcDao<T extends Identified<K>, K> implements GenericDao<T, K> {
    /*TODO change logger*/
    static Logger log = Logger.getLogger(AbstractJdbcDao.class.getSimpleName());

    static {
        new DOMConfigurator().doConfigure("src\\main\\resources\\log4j.xml", LogManager.getLoggerRepository());
    }

    protected abstract String getSelectQuery();

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getDeleteQuery();

    /**
     * Method to get implementor specific type of primary key
     * from result set of generated keys
     */
    protected abstract K getGeneratedKey(ResultSet rs) throws DaoException;

    protected abstract void setGeneratedKey(T object, K genId);

    protected abstract List<T> parseResultSet(ResultSet rs) throws DaoException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws
            DaoException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws
            DaoException, OperationNotSupportedException;

    @Override
    public void add(T object) throws DaoException {
        if (Objects.isNull(object)) {
            log.error("Attempt to add nullable object");
            throw new DaoException("Attempt to add nullable object");
        }

        log.debug("Trying to add object of type " + object.getClass().getSimpleName());
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(getInsertQuery())) {

            prepareStatementForInsert(stmt, object);

            log.debug("Trying to execute insert query");
            stmt.executeUpdate();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public K addWithKeyReturn(T object) throws DaoException {
        if (Objects.isNull(object)) {
            log.error("Attempt to add nullable object");
            throw new DaoException("Attempt to add nullable object");
        }

        log.debug("Trying to add object of type " + object.getClass().getSimpleName());
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(getInsertQuery(), Statement.RETURN_GENERATED_KEYS)) {

            prepareStatementForInsert(stmt, object);

            log.debug("Trying to execute insert query");
            stmt.executeUpdate();

            log.debug("Getting generated id from result set");
            ResultSet rs = stmt.getGeneratedKeys();

            K genKey = getGeneratedKey(rs);
            setGeneratedKey(object, genKey);

            return genKey;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public T getByKey(K key) throws DaoException {
        if (Objects.isNull(key)) {
            log.error("Attempt to get object by nullable key");
            throw new DaoException("Attempt to get object by nullable key");
        }

        List<T> objsByKey;
        String sqlQuery = getSelectQuery() + " WHERE id = ?";

        log.debug("Trying to get object by PK");
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            stmt.setObject(1, key);

            log.debug("Trying to execute select query.");
            ResultSet rs = stmt.executeQuery();
            objsByKey = parseResultSet(rs);

            if (objsByKey == null || objsByKey.size() == 0) {
                log.debug("No objects by given PK were founded");
                throw new DaoException("No objects by given PK were founded");
            }

            log.debug("Object by given PK was successfully founded");
            return objsByKey.iterator().next();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(T object) throws DaoException {
        if (object == null || object.getId() == null) {
            log.error("Attempt to update nullable object or object with nullable id");
            throw new DaoException("Attempt to update nullable object or object with nullable id");
        }

        log.debug("Trying to update object of type " + object.getClass().getSimpleName());
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(getUpdateQuery())) {
            prepareStatementForUpdate(stmt, object);

            log.debug("Trying to execute update query");
            int count = stmt.executeUpdate();

            return count == 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(K key) throws DaoException {
        if (key == null) {
            log.debug("Attempt to delete by nullable key");
            throw new DaoException("Attempt to delete by nullable key");
        }

        log.debug("Trying to delete object");
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(getDeleteQuery())) {
            stmt.setObject(1, key);

            log.debug("Trying to execute delete query");
            int count = stmt.executeUpdate();

            return count == 1;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public List<T> getAll() throws DaoException {
        List<T> list;

        log.debug("Trying to get all objects from table");
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(getSelectQuery())) {

            log.debug("Trying to execute select query");
            ResultSet rs = stmt.executeQuery();

            list = parseResultSet(rs);
            return list;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }
}
