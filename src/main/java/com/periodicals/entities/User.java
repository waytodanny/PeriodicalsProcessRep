package com.periodicals.entities;

import com.periodicals.entities.util.Identified;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 * <p>
 * Class that represents user of system;
 * Each user has definite role
 * @see Role
 */
public class User implements Identified<UUID> {
    private static final int MAX_LOGIN_LENGTH = 50;
    private static final int EMAIL_MAX_LENGTH = 254;

    private UUID id;
    private String login;
    private String password;
    private String email;
    private Role role;

    public User() {

    }

    public User(String login, String password) throws IllegalArgumentException {
        this();
        setLogin(login);
        setPassword(password);
    }

    public User(String login, String password, String email) throws IllegalArgumentException {
        this(login, password);
        setEmail(email);
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) throws IllegalArgumentException {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) throws IllegalArgumentException {
        if (Objects.isNull(login) || login.length() > MAX_LOGIN_LENGTH) {
            throw new IllegalArgumentException(
                    "login length must not be null and have length less than: " + MAX_LOGIN_LENGTH);
        }
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    /*TODO decide which cypher to use*/
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    /*TODO decide whether is right to check here by regex*/
    public void setEmail(String email) throws IllegalArgumentException {
        if (Objects.isNull(email) || email.length() > EMAIL_MAX_LENGTH) {
            throw new IllegalArgumentException();
        }
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, email, role);
    }
}
