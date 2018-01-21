package com.periodicals.command.common;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.services.LoginService;
import com.periodicals.utils.propertyManagers.LanguagePropsManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;
import java.util.Objects;


import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.services.RoleService.ADMIN_ROLE;
import static com.periodicals.services.RoleService.USER_ROLE;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.ATTR_LOGIN;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.ATTR_PASSWORD;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.ATTR_USER;
import static com.periodicals.utils.ResourceHolders.MessagesHolder.LOGIN_ERROR_MESSAGE;
import static com.periodicals.utils.ResourceHolders.PagesHolder.*;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_DEFAULT_PAGE;

/**
 * Command that is responsible to handle user-identifying data
 * given from user request and response for it correctly;
 * Implements Command;
 * Uses LoginService to check incoming data;
 *
 * @see Command
 * @see LoginService
 */
public class LoginCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class.getSimpleName());
    private static final LoginService loginService = LoginService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if(CommandUtils.isPostMethod(request)) {
            //String referer = CommandUtils.getRefererWithoutServletPath(request);

            Locale locale = request.getLocale();

            String login = request.getParameter(ATTR_LOGIN);
            String password = request.getParameter(ATTR_PASSWORD);

            String[] requiredFields = {
                    login,
                    password
            };

            if (CommandUtils.requiredFieldsNotEmpty(requiredFields)) {
                User user = loginService.getUserIfVerified(login, password);
                if (Objects.nonNull(user)) {
                    this.setUserSession(request, user);
                    return new CommandResult(REDIRECT, this.getUserRedirectPageByRole(user));
                } else {
                    request.setAttribute(LOGIN_ERROR_MESSAGE,
                            LanguagePropsManager.getProperty("login.error.incorrect", locale));
                }
            } else {
                request.setAttribute(LOGIN_ERROR_MESSAGE,
                        LanguagePropsManager.getProperty("login.error.empty", locale));
            }
        }
        return new CommandResult(FORWARD, LOGIN_PAGE);
    }

    private void setUserSession(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute(ATTR_USER, user);
    }

    private String getUserRedirectPageByRole(User user) {
        String redirectPage = DEFAULT_PAGE;
        Role role = user.getRole();

        if(role.equals(ADMIN_ROLE)) {
            redirectPage = ADMIN_DEFAULT_PAGE;
            LOGGER.info("User " + user.getLogin() + " entered as an admin");
        }
        if (role.equals(USER_ROLE)) {
            redirectPage = CATALOG_PAGE;
            LOGGER.info("User " + user.getLogin() + " entered as an user");
        }
        return redirectPage;
    }
}
