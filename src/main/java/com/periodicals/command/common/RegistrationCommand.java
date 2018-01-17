package com.periodicals.command.common;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.exceptions.RegistrationException;
import com.periodicals.services.RegistrationService;
import com.periodicals.utils.propertyManagers.LanguagePropsManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static com.periodicals.command.util.CommandHelper.requiredFieldsNotEmpty;
import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.AttributesHolder.GET;
import static com.periodicals.utils.MessagesHolder.REGISTRATION_ERROR_MESSAGE;
import static com.periodicals.utils.PagesHolder.LOGIN_PAGE;
import static com.periodicals.utils.PagesHolder.REGISTRATION_PAGE;

public class RegistrationCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class.getSimpleName());
    private RegistrationService regService = RegistrationService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Locale locale = req.getLocale();

        if (req.getMethod().equals(GET)) {
            return new CommandResult(req, resp, FORWARD, REGISTRATION_PAGE);
        }

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        String[] reqFields = {login, password, email};
        if (!requiredFieldsNotEmpty(reqFields)) {
            req.setAttribute(REGISTRATION_ERROR_MESSAGE, LanguagePropsManager.getProperty("registration.error.empty", locale));
            return new CommandResult(req, resp, FORWARD, REGISTRATION_PAGE);
        }

        try {
            regService.register(login, password, email);
            return new CommandResult(req, resp, REDIRECT, LOGIN_PAGE);
        } catch (RegistrationException e) {
            req.setAttribute(REGISTRATION_ERROR_MESSAGE, LanguagePropsManager.getProperty("registration.error.exists", locale));
            return new CommandResult(req, resp, FORWARD, REGISTRATION_PAGE);
        }
    }
}
