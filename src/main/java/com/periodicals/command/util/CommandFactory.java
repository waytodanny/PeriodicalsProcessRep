package com.periodicals.command.util;

import com.periodicals.command.Command;
import com.periodicals.command.admin.*;
import com.periodicals.command.common.DefaultCommand;
import com.periodicals.command.common.LoginCommand;
import com.periodicals.command.common.RegistrationCommand;
import com.periodicals.command.auth.*;

import java.util.HashMap;
import java.util.Map;

import static com.periodicals.utils.ResourceHolders.AttributesHolder.*;

public class CommandFactory {
    private static final CommandFactory COMMAND_FACTORY = new CommandFactory();
    private Map<String, Command> commandMap = new HashMap<>();

    private CommandFactory() {
        commandMap.put(LOGIN, new LoginCommand());
        commandMap.put(LOGOUT, new LogoutCommand());
        commandMap.put(REGISTER, new RegistrationCommand());
        commandMap.put(CATALOG, new CatalogCommand());
        commandMap.put(PERIODICAL_ISSUES, new PeriodicalIssuesCommand());
        commandMap.put(ADD_TO_CART, new CartAddCommand());
        commandMap.put(REMOVE_FROM_CART, new CartRemoveCommand());
        commandMap.put(SUBSCRIBE, new ProcessSubscriptionCommand());
        commandMap.put(SUBSCRIPTIONS, new UserSubscriptionsCommand());

        commandMap.put(ADMIN_MAIN, new AdminMainCommand());
        commandMap.put(ADMIN_USERS_EDIT, new AdminUsersCommand());
        commandMap.put(ADMIN_USER_INFO, new AdminUserInfoCommand());
        commandMap.put(ADMIN_CATALOG, new CatalogEditingCommand());
        commandMap.put(ADMIN_ADD_PERIODICAL, new AddPeriodicalCommand());
        commandMap.put(ADMIN_PERIODICAL_ISSUES, new IssuesEditingCommand());
        commandMap.put(ADMIN_ADD_ISSUE, new AddIssueCommand());

        commandMap.put(DEFAULT, new DefaultCommand());
    }

    public static CommandFactory getInstance() {
        return COMMAND_FACTORY;
    }

    public Command getCommand(String command) {
        return commandMap.get(command);
    }
}
