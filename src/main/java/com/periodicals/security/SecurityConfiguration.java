package com.periodicals.security;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.periodicals.utils.ResourceHolders.AttributesHolder.*;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.ADMIN_USER_PAYMENTS;

public class SecurityConfiguration {
    private static final SecurityConfiguration SECURITY_CONFIGURATION = new SecurityConfiguration();

    /*Linked to save order*/
    private Map<String, String> permissions = new LinkedHashMap<>();

    private SecurityConfiguration() {
        permissions.put(LOGIN, "ALL");
        permissions.put(LOGOUT, "AUTH");
        permissions.put(REGISTER, "ALL");
        permissions.put(CATALOG, "ALL");
        permissions.put(PERIODICAL_ISSUES, "ALL");
        permissions.put(SUBSCRIPTIONS, "AUTH");
        permissions.put(SUBSCRIBE, "AUTH");
        permissions.put(REMOVE_FROM_CART, "AUTH");

        /*common for redirecting to login page in case of unauthorized user
        wants to buy something, instead of redirecting to error page due to filter logic*/
        permissions.put(ADD_TO_CART, "ALL");

        permissions.put(ADMIN_MAIN, /*"ADMIN"*/"ALL");
        permissions.put(ADMIN_USERS_EDIT, /*"ADMIN"*/"ALL");
        permissions.put(ADMIN_USER_PAYMENTS, /*"ADMIN"*/"ALL");
        permissions.put(ADMIN_CATALOG, /*"ADMIN"*/"ALL");
        permissions.put(ADMIN_ADD_PERIODICAL, /*"ADMIN"*/"ALL");
        permissions.put(ADMIN_PERIODICAL_ISSUES, /*"ADMIN"*/"ALL");
        permissions.put(ADMIN_ADD_ISSUE, /*"ADMIN"*/"ALL");

        permissions.put(DEFAULT, "ALL");
    }

    public static SecurityConfiguration getInstance() {
        return SECURITY_CONFIGURATION;
    }

    public String getCommandSecurityType(String command) {
        return permissions.get(command);
    }

    public Set<String> getEndPoints() {
        return permissions.keySet();
    }
}
