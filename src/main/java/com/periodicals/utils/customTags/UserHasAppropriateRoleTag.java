package com.periodicals.utils.customTags;

import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.services.lookup.RoleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Objects;

import static com.periodicals.utils.ResourceHolders.AttributesHolder.ATTR_USER;


/**
 * Custom JSP tag for checking user roles directly in the JSP page
 */
public class UserHasAppropriateRoleTag extends TagSupport {
    private String role;

    @Override
    public int doStartTag() {
        if (Objects.isNull(role)) {
            if (isUserRoleDefined()) {
                return EVAL_BODY_INCLUDE;
            }
        } else {
            if (userHasSameRole()) {
                return EVAL_BODY_INCLUDE;
            }
        }
        return SKIP_BODY;
    }

    private boolean userHasSameRole() {
        boolean result = false;
        Role userRole = getUserRole();
        if (Objects.nonNull(userRole)) {
            result = Objects.equals(role, userRole.getName());
        }
        return result;
    }

    private boolean isUserRoleDefined() {
        boolean result = false;
        Role userRole = getUserRole();
        return Objects.nonNull(userRole);
    }

    private Role getUserRole(){
        Role role = null;
        HttpSession session = getRequest().getSession(false);
        if (Objects.nonNull(session)) {
            User user = (User) session.getAttribute(ATTR_USER);
            role = user.getRole();
        }
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) pageContext.getRequest();
    }
}
