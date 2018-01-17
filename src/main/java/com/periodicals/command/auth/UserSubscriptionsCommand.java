package com.periodicals.command.auth;

import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.User;
import com.periodicals.services.UserSubscriptionsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.periodicals.authentification.AuthenticationHelper.isUserLoggedIn;
import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.PagesHolder.LOGIN_PAGE;
import static com.periodicals.utils.PagesHolder.USER_SUBSCRIPTIONS_PAGE;

/**
 * Command that uses pagination to display user subscriptions(periodicals)
 *
 * @see CatalogCommand
 */
public class UserSubscriptionsCommand extends CatalogCommand {
    private UserSubscriptionsService subsService = UserSubscriptionsService.getInstance();
    private HttpSession session;

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        session = req.getSession();
        if (!isUserLoggedIn(session)) {
            return new CommandResult(req, resp, FORWARD, LOGIN_PAGE);
        }
        return super.execute(req, resp);
    }

    @Override
    protected PeriodicalsRequestData getPeriodicalsRequestData(HttpServletRequest req) {
        User user = (User) session.getAttribute("user");
        return getPeriodicalsRequestDataByUser(req, user);
    }

    private PeriodicalsRequestData getPeriodicalsRequestDataByUser(HttpServletRequest req, User user) {
        PeriodicalsRequestData data = new PeriodicalsRequestData();
        data.currentPage = getPageFromRequest(req);
        data.periodicals = subsService.getUserSubscriptionsDtoSublist(user,
                (data.currentPage - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);

        data.recordsCount = subsService.getUserSubscriptionsCount(user);
        data.pageLink = USER_SUBSCRIPTIONS_PAGE + "?page=" + data.currentPage;
        return data;
    }
}
