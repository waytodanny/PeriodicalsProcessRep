package com.periodicals.dao.interfaces;

import com.periodicals.exceptions.DaoException;

import java.util.List;

/**
 * Created by Daniel Volnitsky on 23.12.17
 * Dao pattern interface for all dao implementors
 *
 * @T  type of object implementor dao working with
 * @K  primary key type
 */
public interface GenericDao<T, K> {
    void add(T element) throws DaoException;

    K addWithKeyReturn(T element) throws DaoException;

    T getByKey(K key) throws DaoException;

    boolean update(T object) throws DaoException;

    boolean delete(K key) throws DaoException;

    List<T> getAll() throws DaoException;
}
