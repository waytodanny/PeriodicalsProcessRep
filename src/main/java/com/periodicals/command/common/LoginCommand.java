package com.periodicals.command.common;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.User;
import com.periodicals.services.LoginService;
import com.periodicals.utils.propertyManagers.LanguagePropsManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Objects;

import static com.periodicals.command.util.CommandHelper.isGetMethod;
import static com.periodicals.command.util.CommandHelper.requiredFieldsNotEmpty;
import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.services.RoleService.ADMIN_ROLE;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.ATTR_LOGIN;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.ATTR_PASSWORD;
import static com.periodicals.utils.ResourceHolders.MessagesHolder.LOGIN_ERROR_MESSAGE;
import static com.periodicals.utils.ResourceHolders.PagesHolder.*;

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
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {

        if (isGetMethod(req)) {
            return new CommandResult(req, resp, FORWARD, LOGIN_PAGE);
        }

        Locale locale = req.getLocale();

        String enterLogin = req.getParameter(ATTR_LOGIN);
        String enterPass = req.getParameter(ATTR_PASSWORD);

        String[] requiredFields = {enterLogin, enterPass};
        if (requiredFieldsNotEmpty(requiredFields)) {
            User user = loginService.getUserIfVerified(enterLogin, enterPass);
            if (Objects.nonNull(user)) {
                req.getSession().setAttribute("user", user);
                if (user.getRole().equals(ADMIN_ROLE)) {
                    LOGGER.info("User " + user.getLogin() + " entered as an admin");
                    return new CommandResult(req, resp, REDIRECT, ADMIN_MAIN_PAGE);
                } else {
                    LOGGER.info("User " + user.getLogin() + " entered as an user");
                    return new CommandResult(req, resp, REDIRECT, CATALOG_PAGE);
                }
            }
            req.setAttribute(LOGIN_ERROR_MESSAGE, LanguagePropsManager.getProperty("login.error.incorrect", locale));
            return new CommandResult(req, resp, FORWARD, LOGIN_PAGE);
        }
        req.setAttribute(LOGIN_ERROR_MESSAGE, LanguagePropsManager.getProperty("login.error.empty", locale));
        return new CommandResult(req, resp, FORWARD, LOGIN_PAGE);
    }
}
