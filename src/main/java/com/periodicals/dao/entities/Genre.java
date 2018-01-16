package com.periodicals.dao.entities;

import com.periodicals.dao.entities.util.Identified;

import java.util.Objects;

public class Genre implements Identified<Short> {
    private static final int GENRE_NAME_MAX_LENGTH = 100;

    private Short id;
    private String name;

    public Genre() {

    }

    public Genre(String name) throws IllegalArgumentException {
        this();
        this.name = name;
    }

    @Override
    public Short getId() {
        return id;
    }

    public void setId(Short id) throws IllegalArgumentException {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        this.id = id;
    }

    public String getName() throws IllegalArgumentException {
        return name;
    }

    public void setName(String name) {
        if (Objects.isNull(name) || name.length() > GENRE_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "genre length must not be null and have length less than: " + GENRE_NAME_MAX_LENGTH);
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre that = (Genre) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
