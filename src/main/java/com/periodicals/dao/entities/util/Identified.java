package com.periodicals.dao.entities.util;

/**
 * Interface for identified entities
 * Created by Daniel Volnitsky 23.12.2017
 *
 * @param <K> type of id field
 */
public interface Identified<K> {
    K getId();
}

