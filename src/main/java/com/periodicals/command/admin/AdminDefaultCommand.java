package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_DEFAULT_PAGE;

public class AdminDefaultCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return new CommandResult(FORWARD, ADMIN_DEFAULT_PAGE);
    }
}
