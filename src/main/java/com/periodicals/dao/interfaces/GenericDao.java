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
    K add(T element) throws DaoException;

    T getById(K key) throws DaoException;

    void update(T object) throws DaoException;

    void delete(K key) throws DaoException;

    List<T> getAll() throws DaoException;
}
