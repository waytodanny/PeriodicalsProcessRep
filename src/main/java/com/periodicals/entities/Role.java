package com.periodicals.entities;

import com.periodicals.entities.util.Identified;

import java.util.Objects;
import java.util.UUID;

public class Role implements Identified<UUID> {
    private static final int MAX_ROLE_NAME_LENGTH = 50;

    private UUID id;
    private String name;

    public Role() {

    }

    public Role(UUID id, String name) throws IllegalArgumentException {
        setId(id);
        setName(name);
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) throws IllegalArgumentException {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (Objects.isNull(name) || name.length() > MAX_ROLE_NAME_LENGTH) {
            throw new IllegalArgumentException(
                    "role name must not be null and have length less than : " + MAX_ROLE_NAME_LENGTH);
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
