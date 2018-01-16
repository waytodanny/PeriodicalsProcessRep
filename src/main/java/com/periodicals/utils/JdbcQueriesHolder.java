package com.periodicals.utils;

import com.periodicals.utils.propertyManagers.JdbcQueriesPropertyManager;

public class JdbcQueriesHolder {
    /*params*/
    public static final String COUNT_PARAM = JdbcQueriesPropertyManager.getProperty("param.count");

    /*queries*/
    public static final String USER_INSERT = JdbcQueriesPropertyManager.getProperty("user.insert");
    public static final String USER_SELECT_BY_ID = JdbcQueriesPropertyManager.getProperty("user.select.one.id");
    public static final String USER_SELECT_BY_LOGIN = JdbcQueriesPropertyManager.getProperty("user.select.one.login");
    public static final String USER_UPDATE = JdbcQueriesPropertyManager.getProperty("user.update");
    public static final String USER_DELETE = JdbcQueriesPropertyManager.getProperty("user.delete");
    public static final String USER_SELECT_ALL = JdbcQueriesPropertyManager.getProperty("user.select.all");
    public static final String USER_ENTRIES_COUNT = JdbcQueriesPropertyManager.getProperty("user.entries.count");
    public static final String USER_SELECT_SUBLIST = JdbcQueriesPropertyManager.getProperty("user.select.sublist");
    private JdbcQueriesHolder() {

    }
}
