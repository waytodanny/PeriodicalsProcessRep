package com.periodicals.command.common;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.exceptions.RegistrationException;
import com.periodicals.services.RegistrationService;
import com.periodicals.utils.propertyManagers.LanguagePropsManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.*;
import static com.periodicals.utils.ResourceHolders.MessagesHolder.REGISTRATION_ERROR_MESSAGE;
import static com.periodicals.utils.ResourceHolders.PagesHolder.CATALOG_PAGE;
import static com.periodicals.utils.ResourceHolders.PagesHolder.REGISTRATION_PAGE;

/**
 * Command that handles registration requests
 */
public class RegistrationCommand implements Command {
    private RegistrationService registrationService = RegistrationService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if(CommandUtils.isPostMethod(request)) {
            //String referer = CommandUtils.getRefererWithoutServletPath(request);

            Locale locale = request.getLocale();

            String login = request.getParameter(ATTR_LOGIN);
            String password = request.getParameter(ATTR_PASSWORD);
            String email = request.getParameter(ATTR_EMAIL);

            String[] requiredFields = {
                    login,
                    password,
                    email
            };

            if (CommandUtils.requiredFieldsNotEmpty(requiredFields)) {
                try {
                    registrationService.register(login, password, email);
                    return new CommandResult(REDIRECT, CATALOG_PAGE);
                } catch (RegistrationException e) {
                    request.setAttribute(REGISTRATION_ERROR_MESSAGE,
                            LanguagePropsManager.getProperty("registration.error.exists", locale));
                }
            } else {
                request.setAttribute(REGISTRATION_ERROR_MESSAGE,
                        LanguagePropsManager.getProperty("registration.error.empty", locale));
            }
        }
        return new CommandResult(FORWARD, REGISTRATION_PAGE);
    }
}
