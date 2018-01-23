package com.periodicals.authentification;

import com.periodicals.entities.User;
import com.periodicals.services.lookup.RoleService;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.AttributesHolder.ATTR_USER;

/**
 * Class that contains methods that help to get some authentication info from session
 */
public class AuthenticationHelper {
    private static final UUID ADMIN_ID = RoleService.ADMIN_ROLE_ID;

    public static User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(ATTR_USER);
    }

    public static boolean isUserLoggedIn(HttpSession session) {
        User user = getUserFromSession(session);
        return Objects.nonNull(user);
    }

    public static boolean isSessionUserAdmin(HttpSession session) {
        User user = getUserFromSession(session);
        return isAdmin(user);
    }

    private static boolean isAdmin(User user) {
        return Objects.nonNull(user) &&
                Objects.nonNull(user.getRole()) &&
                user.getRole().getId().equals(ADMIN_ID);
    }
}
