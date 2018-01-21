package com.periodicals.authentification;

import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.services.RoleService;

import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.periodicals.utils.ResourceHolders.AttributesHolder.ATTR_USER;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.ROLE_ADMIN_ATTRIBUTE;

/**
 * Class that contains methods that help to get some authentication info from session
 */
public class AuthenticationHelper {
    /*TODO think of is it appropriate to make such variable*/
    private static final Role ADMIN_ROLE = RoleService.getInstance().getRole(ROLE_ADMIN_ATTRIBUTE);

    public static User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute(ATTR_USER);
    }

    public static boolean isUserLoggedIn(HttpSession session) {
        User user = getUserFromSession(session);
        return Objects.nonNull(user);
    }

    public static boolean isCurrentUserAdmin(HttpSession session) {
        User user = getUserFromSession(session);
        return isAdmin(user);
    }

    public static boolean isAdmin(User user) {
        return Objects.nonNull(user) &&
                Objects.nonNull(user.getRole()) &&
                user.getRole().equals(ADMIN_ROLE);
    }
}
