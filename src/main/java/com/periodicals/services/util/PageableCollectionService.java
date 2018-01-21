package com.periodicals.services.util;

import java.util.List;

public interface PageableCollectionService<T> {
    int getEntitiesCount();

    List<T> getEntitiesListBounded(int skip, int limit);
}
