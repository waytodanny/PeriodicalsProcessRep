package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.services.SubscriptionService;
import com.periodicals.services.interfaces.PageableCollectionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.PagesHolder.USER_SUBSCRIPTIONS_PAGE;

/**
 * Command that uses pagination to display user subscriptions (periodicals)
 *
 * @see
 */
public class DisplayUserSubscriptionsCommand extends PagedCommand<Periodical> {
    private SubscriptionService subscriptionService = SubscriptionService.getInstance();

    public PaginationInfoHolder<Periodical> getPaginationInfoHolderInstance(HttpServletRequest request) {
        User user = AuthenticationHelper.getUserFromSession(request.getSession());
        if (Objects.nonNull(user)) {
            return getPeriodicalsByUserPaginationInfoHolder(request, user.getId());
        }
        return null;
    }

    @Override
    protected PageableCollectionService<Periodical> getPageableCollectionService() {
        return subscriptionService;
    }

    @Override
    protected String getPageHrefTemplate() {
        return USER_SUBSCRIPTIONS_PAGE;
    }

    private PaginationInfoHolder<Periodical> getPeriodicalsByUserPaginationInfoHolder(HttpServletRequest request, UUID userId) {
        PaginationInfoHolder<Periodical> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = subscriptionService.getPeriodicalsByUserCount(userId);
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(this.getRecordsPerPage());

        List<Periodical> displayedObjects = subscriptionService.getPeriodicalsByUserListBounded
                (holder.getSkippedRecordsCount(), holder.getRecordsPerPage(), userId);
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(this.getPageHrefTemplate());

        return holder;
    }
}
