package com.periodicals.command.all;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.dto.UserDto;
import com.periodicals.entities.Periodical;
import com.periodicals.services.LoginService;
import com.periodicals.services.UserSubscriptionsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.AttributesHolder.*;
import static com.periodicals.utils.PagesHolder.*;

public class LoginCommand implements Command {
    private LoginService loginService = LoginService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getMethod().equals(GET)) {
            return new CommandResult(req, resp, FORWARD, LOGIN_PAGE);
        }

        String enterLogin = req.getParameter("login");
        String enterPass = req.getParameter("password");

        if (enterLogin.equals("") || enterPass.equals("")) {
            req.setAttribute("loginErrorMessage", "Credentials must not empty");
            return new CommandResult(req, resp, FORWARD, LOGIN_PAGE);
        }

        UserDto user = loginService.getIfVerified(enterLogin, enterPass);
        if (Objects.nonNull(user)) {
            req.getSession().setAttribute("user", user);
            if (user.getRole().getName().equals(ROLE_ADMIN)) {
                return new CommandResult(req, resp, REDIRECT, ADMIN_MAIN_PAGE);
            } else {
                return new CommandResult(req, resp, REDIRECT, CATALOG_PAGE);
            }
        } else {
            req.setAttribute("loginErrorMessage", "Credentials are incorrect");
            return new CommandResult(req, resp, FORWARD, LOGIN_PAGE);
        }
    }
}
