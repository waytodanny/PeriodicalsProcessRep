package com.periodicals.dao.entities.util;

import java.util.Objects;

public class Role implements Identified<Byte>, Cloneable {
    private static final int MAX_ROLE_NAME_LENGTH = 50;

    private Byte id;
    private String name;

    public Role() {

    }

    public Role(String name) throws IllegalArgumentException {
        this();
        setName(name);
    }

    @Override
    public Byte getId() {
        return id;
    }

    public void setId(byte id) throws IllegalArgumentException  {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
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
