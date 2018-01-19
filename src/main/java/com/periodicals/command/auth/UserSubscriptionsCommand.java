package com.periodicals.command.auth;

import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.services.UserSubscriptionsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.periodicals.authentification.AuthenticationHelper.isUserLoggedIn;
import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.ResourceHolders.PagesHolder.LOGIN_PAGE;
import static com.periodicals.utils.ResourceHolders.PagesHolder.USER_SUBSCRIPTIONS_PAGE;

/**
 * Command that uses pagination to display user subscriptions(periodicals)
 *
 * @see CatalogCommand
 */
public class UserSubscriptionsCommand extends CatalogCommand {
    private static final int RECORDS_PER_PAGE = 10;
    private UserSubscriptionsService subsService = UserSubscriptionsService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        if (!isUserLoggedIn(req.getSession())) {
            return new CommandResult(req, resp, FORWARD, LOGIN_PAGE);
        }
        return super.execute(req, resp);
    }

    public PaginationInfoHolder<Periodical, Integer> getPaginationInfo(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("user");
        return getPeriodicalsRequestDataByUser(req, user);
    }

    private PaginationInfoHolder<Periodical, Integer> getPeriodicalsRequestDataByUser(HttpServletRequest req, User user) {
        PaginationInfoHolder<Periodical, Integer> holder = new PaginationInfoHolder<>();

        int currentPage = CommandHelper.getPageFromRequest(req);
        holder.setCurrentPage(currentPage);

        List<Periodical> displayedSublist = subsService.getUserSubscriptionsSublist(user,
                (currentPage - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        holder.setDisplayedObjects(displayedSublist);

        int recordsCount = Math.toIntExact(subsService.getUserSubscriptionsCount(user));
        holder.setRecordsCount(recordsCount);

        holder.setRedirectedPageLink(USER_SUBSCRIPTIONS_PAGE + "?page=" + currentPage);
        holder.setNextPageHrefLink(USER_SUBSCRIPTIONS_PAGE + "?page=" + currentPage + 1);

        return holder;
    }
}
