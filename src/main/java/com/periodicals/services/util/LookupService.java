package com.periodicals.services.util;

import java.util.List;

public interface LookupService<T, E> {
    public T getEntityByPrimaryKey(E key);
    public List<T> getEntityCollection();
}
