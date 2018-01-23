package com.periodicals.command.admin;

import com.periodicals.command.util.PagedCommand;
import com.periodicals.entities.Periodical;
import com.periodicals.services.entities.PeriodicalService;
import com.periodicals.services.interfaces.PageableCollectionService;

import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_PERIODICALS_EDIT_PAGE;

/**
 * @author Daniel Volnitsky
 *
 * Admin command that is responsible for displaying periodicals limited lists
 *
 * @see com.periodicals.entities.PeriodicalIssue
 */
public class AdminPeriodicalsCommand extends PagedCommand<Periodical> {

    @Override
    protected PageableCollectionService<Periodical> getPageableCollectionService() {
        return PeriodicalService.getInstance();
    }

    @Override
    protected String getPageHrefTemplate() {
        return ADMIN_PERIODICALS_EDIT_PAGE;
    }
}
