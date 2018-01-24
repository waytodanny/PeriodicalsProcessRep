package com.periodicals.command.common;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.services.entities.UserService;
import com.periodicals.services.lookups.RoleService;
import com.periodicals.utils.propertyManagers.LanguagePropsManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.resourceHolders.AttributesHolder.*;
import static com.periodicals.utils.resourceHolders.MessagesHolder.REGISTRATION_ERROR_MESSAGE;
import static com.periodicals.utils.resourceHolders.PagesHolder.CATALOG_PAGE;
import static com.periodicals.utils.resourceHolders.PagesHolder.REGISTRATION_PAGE;

/**
 * @author Daniel Volnitsky
 * Command that is responsible to handle registration requests
 */
public class RegistrationCommand implements Command {
    private static UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if (CommandUtils.isPostMethod(request)) {
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
                    userService.createEntity(
                            login,
                            password,
                            email,
                            RoleService.USER_ROLE_ID);

                    return new CommandResult(REDIRECT, CATALOG_PAGE);
                } catch (ServiceException e) {
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
