package com.periodicals.utils;

import com.periodicals.utils.propertyManagers.JdbcQueriesPropertyManager;

public class JdbcQueriesHolder {
    /*params*/
    public static final String COUNT_PARAM = JdbcQueriesPropertyManager.getProperty("param.count");

    /*queries*/

    /*user table*/
    public static final String USER_INSERT = JdbcQueriesPropertyManager.getProperty("user.insert");
    public static final String USER_SELECT_BY_ID = JdbcQueriesPropertyManager.getProperty("user.select.one.id");
    public static final String USER_SELECT_BY_LOGIN = JdbcQueriesPropertyManager.getProperty("user.select.one.login");
    public static final String USER_UPDATE = JdbcQueriesPropertyManager.getProperty("user.update");
    public static final String USER_DELETE = JdbcQueriesPropertyManager.getProperty("user.delete");
    public static final String USER_SELECT_ALL = JdbcQueriesPropertyManager.getProperty("user.select.all");
    public static final String USER_ENTRIES_COUNT = JdbcQueriesPropertyManager.getProperty("user.entries.count");
    public static final String USER_SELECT_SUBLIST = JdbcQueriesPropertyManager.getProperty("user.select.sublist");

    /*role table*/
    public static final String ROLE_INSERT = JdbcQueriesPropertyManager.getProperty("role.insert");
    public static final String ROLE_SELECT_BY_ID = JdbcQueriesPropertyManager.getProperty("role.select.one.id");
    public static final String ROLE_SELECT_BY_NAME = JdbcQueriesPropertyManager.getProperty("role.select.one.name");
    public static final String ROLE_UPDATE = JdbcQueriesPropertyManager.getProperty("role.update");
    public static final String ROLE_DELETE = JdbcQueriesPropertyManager.getProperty("role.delete");
    public static final String ROLE_SELECT_ALL = JdbcQueriesPropertyManager.getProperty("role.select.all");
    public static final String ROLE_ENTRIES_COUNT = JdbcQueriesPropertyManager.getProperty("role.entries.count");
    public static final String ROLE_SELECT_SUBLIST = JdbcQueriesPropertyManager.getProperty("role.select.sublist");

    /*publishers table*/
    public static final String PUBLISHER_INSERT = JdbcQueriesPropertyManager.getProperty("publisher.insert");
    public static final String PUBLISHER_SELECT_BY_ID = JdbcQueriesPropertyManager.getProperty("publisher.select.one.id");
    public static final String PUBLISHER_SELECT_BY_NAME = JdbcQueriesPropertyManager.getProperty("publisher.select.one.name");
    public static final String PUBLISHER_UPDATE = JdbcQueriesPropertyManager.getProperty("publisher.update");
    public static final String PUBLISHER_DELETE = JdbcQueriesPropertyManager.getProperty("publisher.delete");
    public static final String PUBLISHER_SELECT_ALL = JdbcQueriesPropertyManager.getProperty("publisher.select.all");
    public static final String PUBLISHER_ENTRIES_COUNT = JdbcQueriesPropertyManager.getProperty("publisher.entries.count");
    public static final String PUBLISHER_SELECT_SUBLIST = JdbcQueriesPropertyManager.getProperty("publisher.select.sublist");

    private JdbcQueriesHolder() {

    }
}