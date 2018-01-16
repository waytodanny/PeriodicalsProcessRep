package com.periodicals.command.all;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.exceptions.RegistrationException;
import com.periodicals.services.RegistrationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.periodicals.command.util.CommandHelper.requiredFieldsNotEmpty;
import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.AttributesHolder.GET;
import static com.periodicals.utils.PagesHolder.LOGIN_PAGE;
import static com.periodicals.utils.PagesHolder.REGISTRATION_PAGE;

public class RegistrationCommand implements Command {
    private RegistrationService reqistrService = RegistrationService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        if (req.getMethod().equals(GET)) {
            return new CommandResult(req, resp, FORWARD, REGISTRATION_PAGE);
        }

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        String[] reqFields = {login, password, email};

        if (!requiredFieldsNotEmpty(reqFields)) {
            req.setAttribute("registrErrorMessage", "All fields must been not empty");
            return new CommandResult(req, resp, FORWARD, REGISTRATION_PAGE);
        }

        try {
            reqistrService.register(login, password, email);
            return new CommandResult(req, resp, REDIRECT, LOGIN_PAGE);
        } catch (RegistrationException e) {
            req.setAttribute("registrErrorMessage", e.getMessage());
            return new CommandResult(req, resp, FORWARD, REGISTRATION_PAGE);
        }
    }
}
