package com.periodicals.authentification;

import com.periodicals.dao.entities.Role;
import com.periodicals.dao.entities.User;
import com.periodicals.services.RoleService;

import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.periodicals.utils.AttributesHolder.ROLE_ADMIN;
import static com.periodicals.utils.AttributesHolder.USER;

/**
 * Class that contains method that help to get some authentication info
 */
/*TODO преесмотреть создание статик админ*/
public class AuthenticationHelper {
    private static final Role adminRole = RoleService.getInstance().getRole(ROLE_ADMIN);

    public static boolean isUserLoggedIn(HttpSession session) {
        User user = (User) session.getAttribute(USER);
        return Objects.nonNull(user);
    }

    public static boolean isAdmin(User user) {
        return Objects.nonNull(user) &&
                Objects.nonNull(user.getRole()) &&
                user.getRole().equals(adminRole);
    }
}
