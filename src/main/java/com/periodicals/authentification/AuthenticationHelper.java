package com.periodicals.authentification;

import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.services.RoleService;

import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.periodicals.utils.ResourceHolders.AttributesHolder.ROLE_ADMIN;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.ATTR_USER;

/**
 * Class that contains method that help to get some authentication info
 */
/*TODO преесмотреть создание статик админ*/
public class AuthenticationHelper {
    private static final Role adminRole = RoleService.getInstance().getRole(ROLE_ADMIN);

    public static boolean isUserLoggedIn(HttpSession session) {
        User user = (User) session.getAttribute(ATTR_USER);
        return Objects.nonNull(user);
    }

    public static boolean isAdmin(User user) {
        return Objects.nonNull(user) &&
                Objects.nonNull(user.getRole()) &&
                user.getRole().equals(adminRole);
    }
}
