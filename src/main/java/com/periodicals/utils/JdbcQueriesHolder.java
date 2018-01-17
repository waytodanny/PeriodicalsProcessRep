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

    /*genres table*/
    public static final String GENRE_INSERT = JdbcQueriesPropertyManager.getProperty("genre.insert");
    public static final String GENRE_SELECT_BY_ID = JdbcQueriesPropertyManager.getProperty("genre.select.one.id");
    public static final String GENRE_SELECT_BY_NAME = JdbcQueriesPropertyManager.getProperty("genre.select.one.name");
    public static final String GENRE_UPDATE = JdbcQueriesPropertyManager.getProperty("genre.update");
    public static final String GENRE_DELETE = JdbcQueriesPropertyManager.getProperty("genre.delete");
    public static final String GENRE_SELECT_ALL = JdbcQueriesPropertyManager.getProperty("genre.select.all");
    public static final String GENRE_ENTRIES_COUNT = JdbcQueriesPropertyManager.getProperty("genre.entries.count");
    public static final String GENRE_SELECT_SUBLIST = JdbcQueriesPropertyManager.getProperty("genre.select.sublist");

    /*periodicals table*/
    public static final String PERIODICAL_INSERT = JdbcQueriesPropertyManager.getProperty("periodical.insert");
    public static final String PERIODICAL_SELECT_BY_ID = JdbcQueriesPropertyManager.getProperty("periodical.select.one.id");
    public static final String PERIODICAL_SELECT_BY_NAME = JdbcQueriesPropertyManager.getProperty("periodical.select.one.name");
    public static final String PERIODICAL_UPDATE = JdbcQueriesPropertyManager.getProperty("periodical.update");
    public static final String PERIODICAL_DELETE = JdbcQueriesPropertyManager.getProperty("periodical.delete");
    public static final String PERIODICAL_SELECT_ALL = JdbcQueriesPropertyManager.getProperty("periodical.select.all");
    public static final String PERIODICAL_ENTRIES_COUNT = JdbcQueriesPropertyManager.getProperty("periodical.entries.count");
    public static final String PERIODICAL_SELECT_SUBLIST = JdbcQueriesPropertyManager.getProperty("periodical.select.sublist");
    public static final String PERIODICAL_SELECT_SUBLIST_BY_GENRE = JdbcQueriesPropertyManager.getProperty("periodical.select.sublist.genre");
    public static final String PERIODICAL_ENTRIES_BY_GENRE_COUNT = JdbcQueriesPropertyManager.getProperty("periodical.entries.by.genre.count");
    public static final String SUBSCRIPTIONS_IS_USER_SUBSCRIBED = JdbcQueriesPropertyManager.getProperty("periodical.user.is.subscribed");
    public static final String SUBSCRIPTIONS_SELECT_USER_SUBSCRIPTIONS = JdbcQueriesPropertyManager.getProperty("periodical.select.user.subscriptions");
    public static final String SUBSCRIPTIONS_ADD_USER_SUBSCRIPTION = JdbcQueriesPropertyManager.getProperty("periodical.insert.user.subscription");
    public static final String SUBSCRIPTIONS_USER_SUBSCRIPTIONS_COUNT = JdbcQueriesPropertyManager.getProperty("periodical.user.subscriptions.count");
    public static final String SELECT_PAYMENT_PERIODICALS = JdbcQueriesPropertyManager.getProperty("periodical.select.payment.periodicals");

    /*periodical_issues table*/
    public static final String PERIODICAL_ISSUE_INSERT = JdbcQueriesPropertyManager.getProperty("periodical_issues.insert");
    public static final String PERIODICAL_ISSUE_SELECT_BY_ID = JdbcQueriesPropertyManager.getProperty("periodical_issues.select.one.id");
    public static final String PERIODICAL_ISSUE_UPDATE = JdbcQueriesPropertyManager.getProperty("periodical_issues.update");
    public static final String PERIODICAL_ISSUE_DELETE = JdbcQueriesPropertyManager.getProperty("periodical_issues.delete");
    public static final String PERIODICAL_ISSUE_SELECT_ALL = JdbcQueriesPropertyManager.getProperty("periodical_issues.select.all");
    public static final String PERIODICAL_ISSUE_SELECT_BY_PERIODICAL= JdbcQueriesPropertyManager.getProperty("periodical_issues.select.by.periodical");
    public static final String PERIODICAL_ISSUE_ENTRIES_COUNT = JdbcQueriesPropertyManager.getProperty("periodical_issues.entries.count");
    public static final String PERIODICAL_ISSUE_SELECT_SUBLIST = JdbcQueriesPropertyManager.getProperty("periodical_issues.select.sublist");

    private JdbcQueriesHolder() {

    }
}
