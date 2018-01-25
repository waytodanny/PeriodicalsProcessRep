package com.periodicals.command.admin;

import com.periodicals.command.util.PagedCommand;
import com.periodicals.entities.User;
import com.periodicals.services.entities.UserService;
import com.periodicals.services.interfaces.PageableCollectionService;

import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_USERS_PAGE;

/**
 * @author Daniel Volnitsky
 *
 * Admin command that is responsible for displaying users
 *
 * @see com.periodicals.entities.PeriodicalIssue
 */
public class AdminUsersCommand extends PagedCommand<User> {

    @Override
    protected PageableCollectionService<User> getPageableCollectionService() {
        return UserService.getInstance();
    }

    @Override
    protected String getPageHrefTemplate() {
        return ADMIN_USERS_PAGE;
    }
}
