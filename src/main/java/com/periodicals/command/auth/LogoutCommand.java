package com.periodicals.command.auth;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.ResourceHolders.PagesHolder.LOGIN_PAGE;

/*
 * Command made for logout, it does:
 * 1) invalidates the session;
 * 2) clears the identity information in the request.
 */
public class LogoutCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class.getSimpleName());

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        try {
            session.invalidate();
            request.logout();
            LOGGER.debug("Successful session invalidation");
        } catch (ServletException e) {
            LOGGER.error("Failed to clear request identity information: " + e.getMessage());
        }
        return new CommandResult(REDIRECT, LOGIN_PAGE);
    }
}
