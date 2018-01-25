package com.periodicals.entities.util;

/**
 * @param <K> type of identification field
 * @author Daniel Volnitsky
 * <p>
 * Interface for identified entities
 */
public interface Identified<K> {
    K getId();
}

