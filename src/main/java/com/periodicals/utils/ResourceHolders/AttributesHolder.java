package com.periodicals.utils.ResourceHolders;

import com.periodicals.utils.propertyManagers.AttributesPropertyManager;

public final class AttributesHolder {

    public static final String SERVLET_ROOT = AttributesPropertyManager.getProperty("servlet.root");

    /*Commands*/
    public static final String LOGIN = AttributesPropertyManager.getProperty("command.login");
    public static final String REGISTER = AttributesPropertyManager.getProperty("command.registration");
    public static final String LOGOUT = AttributesPropertyManager.getProperty("command.logout");

    public static final String CATALOG = AttributesPropertyManager.getProperty("command.catalog");
    public static final String PERIODICAL = AttributesPropertyManager.getProperty("command.periodical");
    public static final String PERIODICAL_ISSUES = AttributesPropertyManager.getProperty("command.periodical_issues");

    public static final String SUBSCRIPTIONS = AttributesPropertyManager.getProperty("command.user.subscriptions");
    public static final String ADD_TO_CART = AttributesPropertyManager.getProperty("command.add_to_cart");
    public static final String REMOVE_FROM_CART = AttributesPropertyManager.getProperty("command.remove_from_cart");
    public static final String SUBSCRIBE = AttributesPropertyManager.getProperty("command.subscribe");

    public static final String ADMIN_MAIN = AttributesPropertyManager.getProperty("command.admin.main");
    public static final String ADMIN_USERS = AttributesPropertyManager.getProperty("command.admin.users");
    public static final String ADMIN_USERS_EDIT = AttributesPropertyManager.getProperty("command.admin.users.edit");
    public static final String ADMIN_USERS_DELETE = AttributesPropertyManager.getProperty("command.admin.users.delete");
    public static final String ADMIN_USER_PAYMENTS = AttributesPropertyManager.getProperty("command.admin.user.payments");
    public static final String ADMIN_CATALOG = AttributesPropertyManager.getProperty("command.admin.catalog");
    public static final String ADMIN_PERIODICAL_ISSUES = AttributesPropertyManager.getProperty("command.admin.issues");
    public static final String ADMIN_ADD_PERIODICAL = AttributesPropertyManager.getProperty("command.admin.add.periodical");
    public static final String ADMIN_ADD_ISSUE = AttributesPropertyManager.getProperty("command.admin.add.issue");
    public static final String ADMIN_EDIT_PERIODICAL = AttributesPropertyManager.getProperty("command.admin.edit.periodical");
    public static final String ADMIN_EDIT_ISSUE = AttributesPropertyManager.getProperty("command.admin.edit.issue");
    public static final String ADMIN_DELETE_PERIODICAL = AttributesPropertyManager.getProperty("command.admin.delete.periodical");
    public static final String ADMIN_DELETE_ISSUE = AttributesPropertyManager.getProperty("command.admin.delete.issue");
    public static final String DEFAULT = "/";

    /*additional*/
    public static final String GET = AttributesPropertyManager.getProperty("method.type.get");
    public static final String POST = AttributesPropertyManager.getProperty("method.type.post");
    public static final String PAGE_SUFFIX = AttributesPropertyManager.getProperty("page.suffix");

    public static final String ROLE_ATTR = AttributesPropertyManager.getProperty("attr.role");
    public static final String ROLE_ADMIN = AttributesPropertyManager.getProperty("role.admin");
    public static final String ROLE_USER = AttributesPropertyManager.getProperty("role.user");

    /*Request attributes*/
    public static final String COMMAND = "command";
    public static final String ATTR_USER = AttributesPropertyManager.getProperty("attr.user");

    public static final String ATTR_LOGIN = AttributesPropertyManager.getProperty("attr.login");
    public static final String ATTR_PASSWORD = AttributesPropertyManager.getProperty("attr.pass");
    public static final String ATTR_EMAIL = AttributesPropertyManager.getProperty("attr.email");

    public static final String ATTR_PAGE = AttributesPropertyManager.getProperty("attr.page");
    public static final String ATTR_CART = AttributesPropertyManager.getProperty("attr.cart");

    public static final String ATTR_GENRE = AttributesPropertyManager.getProperty("attr.genre");
    public static final String ATTR_PERIODICAL = AttributesPropertyManager.getProperty("attr.periodical");

    private AttributesHolder() {

    }
}
