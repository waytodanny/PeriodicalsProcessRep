package com.periodicals.dao.interfaces;

import com.periodicals.exceptions.DaoException;

import java.util.List;

/**
 * Created by Daniel Volnitsky on 23.12.17
 * Dao pattern interface for common dao implementors
 *
 * @T type of object implementor dao working with
 * @K primary key type
 */
public interface GenericDao<T, K> {
    void createEntity(T entity) throws DaoException;
    void updateEntity(T entity) throws DaoException;
    void deleteEntity(K key) throws DaoException;
    T getEntityByPrimaryKey(K key) throws DaoException;
    List<T> getEntityCollection() throws DaoException;
    int getEntitiesCount() throws DaoException;
}
