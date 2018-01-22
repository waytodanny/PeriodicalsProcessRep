package com.periodicals.services.util;

import com.periodicals.exceptions.ServiceException;

import java.util.List;

public interface LookupService<T, K> {
    public T getEntityByPrimaryKey(K key) throws ServiceException;

    public List<T> getEntityCollection();
}
