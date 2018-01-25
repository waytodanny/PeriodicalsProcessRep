package com.periodicals.entities;

import com.periodicals.entities.util.Identified;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 * <p>
 * Class that represents publisher
 */
public class Publisher implements Identified<UUID> {
    private static final int PUBLISHER_NAME_MAX_LENGTH = 100;

    private UUID id;
    private String name;

    public Publisher() {

    }

    public Publisher(String name) throws IllegalArgumentException {
        this();
        setName(name);
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (Objects.isNull(name) || name.length() > PUBLISHER_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "publisher name must not be null and have length less than : " + PUBLISHER_NAME_MAX_LENGTH);
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publisher publisher = (Publisher) o;
        return Objects.equals(id, publisher.id) &&
                Objects.equals(name, publisher.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
