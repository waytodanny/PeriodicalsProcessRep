package com.periodicals.entities;

import com.periodicals.entities.util.Identified;

import java.util.Objects;
import java.util.UUID;

public class Genre implements Identified<UUID> {
    private static final int GENRE_NAME_MAX_LENGTH = 100;

    private UUID id;
    private String name;

    public Genre() {

    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) throws IllegalArgumentException {
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
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id) &&
                Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
