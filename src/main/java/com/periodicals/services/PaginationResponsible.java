package com.periodicals.services;

import java.util.List;

public interface PaginationResponsible<T> {
    int getEntitiesCount();
    List<T> getLimitedList(int skip, int limit);
}
