package com.periodicals.utils.ResourceHolders;

import com.periodicals.utils.propertyManagers.PagesPropertyManager;

public final class PagesHolder {
    public static final String INDEX_PAGE = PagesPropertyManager.getProperty("page.index");
    public static final String DEFAULT_PAGE = PagesPropertyManager.getProperty("page.default");
    public static final String ERROR_PAGE = PagesPropertyManager.getProperty("page.error");
    public static final String LOGIN_PAGE = PagesPropertyManager.getProperty("page.login");
    public static final String USER_SUBSCRIPTIONS_PAGE = PagesPropertyManager.getProperty("page.user.subscriptions");
    public static final String CATALOG_PAGE = PagesPropertyManager.getProperty("page.catalog");
    public static final String REGISTRATION_PAGE =  PagesPropertyManager.getProperty("page.registration");
    public static final String PERIODICAL_PAGE = PagesPropertyManager.getProperty("page.periodical");
    public static final String PERIODICAL_ISSUES_PAGE = PagesPropertyManager.getProperty("page.user.issues");

    public static final String ADMIN_DEFAULT_PAGE = PagesPropertyManager.getProperty("page.admin.default");
    public static final String ADMIN_PERIODICALS_EDIT_PAGE = PagesPropertyManager.getProperty("page.admin.periodicals");
    public static final String ADMIN_ADD_PERIODICAL_PAGE = PagesPropertyManager.getProperty("page.admin.add.periodical");
    public static final String ADMIN_PERIODICAL_ISSUE_EDIT_PAGE = PagesPropertyManager.getProperty("page.admin.edit.issue");
    public static final String ADMIN_ADD_ISSUE_PAGE = PagesPropertyManager.getProperty("page.admin.add.issue");
    public static final String ADMIN_USERS_PAGE = PagesPropertyManager.getProperty("page.admin.users");
    public static final String ADMIN_EDIT_USERS_PAGE = PagesPropertyManager.getProperty("page.admin.users.edit");
    public static final String ADMIN_USER_PAYMENTS_PAGE = PagesPropertyManager.getProperty("page.admin.user.payments");
}
