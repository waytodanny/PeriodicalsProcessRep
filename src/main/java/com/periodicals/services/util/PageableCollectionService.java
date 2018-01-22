package com.periodicals.services.util;

import java.util.List;

public interface PageableCollectionService<T> {
    public int getEntitiesCount();
    public List<T> getEntitiesListBounded(int skip, int limit);
}
