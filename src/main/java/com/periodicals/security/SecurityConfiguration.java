package com.periodicals.security;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static com.periodicals.utils.AttributesHolder.*;

public class SecurityConfiguration {
    private static final SecurityConfiguration SECURITY_CONFIG = new SecurityConfiguration();

    /*Linked to save order*/
    private Map<String, String> permissions = new LinkedHashMap<>();

    private SecurityConfiguration() {
        permissions.put(LOGIN, "ALL");
        permissions.put(LOGOUT, "AUTH");
        permissions.put(REGISTER, "ALL");
        permissions.put(CATALOG, "ALL");
        permissions.put(PERIODICAL, "AUTH");
        permissions.put(PERIODICAL_ISSUES, "ALL");
        permissions.put(SUBSCRIPTIONS, "AUTH");
        permissions.put(SUBSCRIBE, "AUTH");

        /*common for redirecting to login page in case of unauthorized user
        wants to buy something, instead of redirecting to error page due to filter logic*/
        permissions.put(ADD_TO_CART, "ALL");

        permissions.put(ADMIN_MAIN, "ADMIN");
        permissions.put(ADMIN_USERS, "ADMIN");
        permissions.put(ADMIN_USER_INFO, "ADMIN");
        permissions.put(ADMIN_CATALOG, "ADMIN");
        permissions.put(ADMIN_ADD_PERIODICAL, "ADMIN");
        permissions.put(ADMIN_PERIODICAL_ISSUES, "ADMIN");
        permissions.put(ADMIN_ADD_ISSUE, "ADMIN");

        permissions.put(DEFAULT, "ALL");
    }

    public static SecurityConfiguration getInstance() {
        return SECURITY_CONFIG;
    }

    public String getSecurityType(String command) {
        return permissions.get(command);
    }

    public Set<String> getEndPoints() {
        return permissions.keySet();
    }
}
