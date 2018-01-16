package com.periodicals.entities;

import com.periodicals.entities.util.Identified;

import java.util.Objects;

public class User implements Identified<String> {
    private static final int UUID_DEFAULT_LENGTH = 36;
    private static final int MAX_LOGIN_LENGTH = 50;
    private static final int EMAIL_MAX_LENGTH = 254;

    private String uuid;
    private String login;
    private String password;
    private String email;
    private Byte roleId;

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
    public String getId() {
        return uuid;
    }

    public void setId(String uuid) throws IllegalArgumentException {
        if (uuid.length() != UUID_DEFAULT_LENGTH) {
            throw new IllegalArgumentException("Invalid uuid length: " + uuid.length());
        }
        this.uuid = uuid;
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

    public Byte getRoleId() {
        return roleId;
    }

    /*TODO decide whether is right to check here*/
    public void setRoleId(Byte roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uuid, user.uuid) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(roleId, user.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, login, password, email, roleId);
    }
}
