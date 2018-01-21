package com.periodicals.entities;

import com.periodicals.entities.util.Identified;

import java.util.Objects;

public class Role implements Identified<String> {
    private static final int UUID_DEFAULT_LENGTH = 36;
    private static final int MAX_ROLE_NAME_LENGTH = 50;

    private String id;
    private String name;

    public Role() {

    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) throws IllegalArgumentException  {
        if (id.length() != UUID_DEFAULT_LENGTH) {
            throw new IllegalArgumentException("Invalid id length: " + id.length());
        }
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
