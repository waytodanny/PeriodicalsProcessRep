package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.services.UserSubscriptionsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.periodicals.utils.ResourceHolders.PagesHolder.USER_SUBSCRIPTIONS_PAGE;

/**
 * Command that uses pagination to display user subscriptions(periodicals)
 *
 * @see
 */
public class UserSubscriptionsCommand extends PagedCommand<Periodical> {
    private static final int RECORDS_PER_PAGE = 10;

    private UserSubscriptionsService subscriptionsService = UserSubscriptionsService.getInstance();

    public PaginationInfoHolder<Periodical> getPaginationInfoHolderInstance(HttpServletRequest request) {
        User user = AuthenticationHelper.getUserFromSession(request.getSession());
        if(Objects.nonNull(user)) {
            return getPeriodicalsByUserPaginationInfoHolder(request, user);
        }
        return null;
    }

    private PaginationInfoHolder<Periodical> getPeriodicalsByUserPaginationInfoHolder(HttpServletRequest request, User user) {
        PaginationInfoHolder<Periodical> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = Math.toIntExact(subscriptionsService.getUserSubscriptionsCount(user));
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(RECORDS_PER_PAGE);

        List<Periodical> displayedObjects = subscriptionsService.getUserSubscriptionsSublist
                (user, holder.getSkippedRecordsCount(), holder.getRecordsPerPage());
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(USER_SUBSCRIPTIONS_PAGE);

        return holder;
    }
}
