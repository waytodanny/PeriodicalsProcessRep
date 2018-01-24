package com.periodicals.command.util;

import com.periodicals.command.admin.*;
import com.periodicals.command.auth.*;
import com.periodicals.command.common.DefaultCommand;
import com.periodicals.command.common.LoginCommand;
import com.periodicals.command.common.RegistrationCommand;

import java.util.HashMap;
import java.util.Map;

import static com.periodicals.utils.resourceHolders.AttributesHolder.*;

/**
 * @author Daniel Volnitsky
 *
 * Class that is responsible for holdng application endpoints and commands that
 * correspond to them
 */
public class CommandFactory {
    private static final CommandFactory COMMAND_FACTORY = new CommandFactory();
    private Map<String, Command> commandMap = new HashMap<>();

    private CommandFactory() {
        commandMap.put(LOGIN, new LoginCommand());
        commandMap.put(LOGOUT, new LogoutCommand());
        commandMap.put(REGISTER, new RegistrationCommand());
        commandMap.put(CATALOG, new DisplayPeriodicalsCommand());
        commandMap.put(PERIODICAL_ISSUES, new DisplayPeriodicalIssuesCommand());
        commandMap.put(ADD_TO_CART, new CartAddItemCommand());
        commandMap.put(REMOVE_FROM_CART, new CartRemoveItemCommand());
        commandMap.put(SUBSCRIBE, new ProcessSubscriptionCommand());
        commandMap.put(SUBSCRIPTIONS, new DisplayUserSubscriptionsCommand());

        commandMap.put(ADMIN_MAIN, new AdminDefaultCommand());
        commandMap.put(ADMIN_USERS, new AdminUsersCommand());
        commandMap.put(ADMIN_USERS_EDIT, new EditUserCommand());
        commandMap.put(ADMIN_USERS_DELETE, new DeleteUserCommand());
        commandMap.put(ADMIN_USER_PAYMENTS, new AdminUserPaymentsCommand());

        commandMap.put(ADMIN_CATALOG, new AdminPeriodicalsCommand());
        commandMap.put(ADMIN_ADD_PERIODICAL, new AddPeriodicalCommand());
        commandMap.put(ADMIN_EDIT_PERIODICAL, new EditPeriodicalCommand());
        commandMap.put(ADMIN_DELETE_PERIODICAL, new DeletePeriodicalCommand());

        commandMap.put(ADMIN_PERIODICAL_ISSUES, new AdminIssuesCommand());
        commandMap.put(ADMIN_ADD_ISSUE, new AddIssueCommand());
        commandMap.put(ADMIN_EDIT_ISSUE, new EditIssueCommand());
        commandMap.put(ADMIN_DELETE_ISSUE, new DeleteIssueCommand());

        commandMap.put(DEFAULT, new DefaultCommand());
    }

    public static CommandFactory getInstance() {
        return COMMAND_FACTORY;
    }

    public Command getCommand(String command) {
        return commandMap.get(command);
    }
}
