package com.periodicals.command.admin;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.PagesHolder.ADMIN_MAIN_PAGE;

public class AdminMainCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        return new CommandResult(req, resp, FORWARD, ADMIN_MAIN_PAGE);
    }
}
