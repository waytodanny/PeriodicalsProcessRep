package com.periodicals.command.auth;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.periodicals.command.util.RedirectType.*;
import static com.periodicals.utils.PagesHolder.LOGIN_PAGE;

public class LogoutCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        try {
            req.logout();
        } catch (ServletException e) {
           /*TODO log*/
        }
        return new CommandResult(req, resp, REDIRECT, LOGIN_PAGE);
    }
}
