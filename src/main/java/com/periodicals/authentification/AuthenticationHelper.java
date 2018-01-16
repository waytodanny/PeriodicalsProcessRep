package com.periodicals.authentification;

import com.periodicals.dto.UserDto;
import com.periodicals.entities.Role;
import com.periodicals.services.RoleService;

import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.periodicals.utils.AttributesHolder.ROLE_ADMIN;
import static com.periodicals.utils.AttributesHolder.USER;

/*TODO преесмотреть создание статик админ*/
public class AuthenticationHelper {
    private static Role adminRole = RoleService.getInstance().getRole(ROLE_ADMIN);

    public static boolean isUserLoggedIn(HttpSession session) {
        UserDto user = (UserDto) session.getAttribute(USER);
        return Objects.nonNull(user);
    }

    public static boolean isAdmin(HttpSession session) {
        UserDto user = (UserDto) session.getAttribute(USER);
        return Objects.nonNull(user) &&
                Objects.nonNull(user.getRole()) &&
                user.getRole().getName().equals(adminRole.getName());
    }
}
