package com.periodicals.command.all;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.dto.UserDto;
import com.periodicals.services.LoginService;
import com.periodicals.utils.propertyManagers.LanguagePropsManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Objects;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.AttributesHolder.ROLE_ADMIN;
import static com.periodicals.utils.MessagesHolder.LOGIN_ERROR_MESSAGE;
import static com.periodicals.utils.PagesHolder.*;

public class LoginCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class.getSimpleName());
    private LoginService loginService = LoginService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Locale locale = req.getLocale();

        String enterLogin = req.getParameter("login");
        String enterPass = req.getParameter("password");

        /*if it is simple get request*/
        if (Objects.isNull(enterLogin) || Objects.isNull(enterPass)) {
            return new CommandResult(req, resp, FORWARD, LOGIN_PAGE);
        }

        if (enterLogin.equals("") || enterPass.equals("")) {
            req.setAttribute(LOGIN_ERROR_MESSAGE, LanguagePropsManager.getProperty("login.error.empty", locale));
            return new CommandResult(req, resp, FORWARD, LOGIN_PAGE);
        }

        UserDto user = loginService.getIfVerified(enterLogin, enterPass);
        if (Objects.nonNull(user)) {
            req.getSession().setAttribute("user", user);
            if (user.getRole().getName().equals(ROLE_ADMIN)) {
                LOGGER.info("User " + user.getLogin() + " entered as an admin");
                return new CommandResult(req, resp, REDIRECT, ADMIN_MAIN_PAGE);
            } else {
                LOGGER.info("User " + user.getLogin() + " entered as an user");
                return new CommandResult(req, resp, REDIRECT, CATALOG_PAGE);
            }
        } else {
            req.setAttribute(LOGIN_ERROR_MESSAGE, LanguagePropsManager.getProperty("login.error.incorrect", locale));
            return new CommandResult(req, resp, FORWARD, LOGIN_PAGE);
        }
    }
}
