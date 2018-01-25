package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.entities.User;
import com.periodicals.services.SubscriptionService;
import com.periodicals.services.entities.PeriodicalIssueService;
import com.periodicals.services.interfaces.PageableCollectionService;
import com.periodicals.utils.uuid.UUIDHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.AttributesHolder.ADMIN_PERIODICAL_ISSUES;

/**
 * @author Daniel Volnitsky
 *
 * Command for authenticated users that is responsible for providing request attributes
 * for view to display paginable periodical issues collection
 *
 * @see PagedCommand
 */
public class DisplayPeriodicalIssuesCommand extends PagedCommand<PeriodicalIssue> {
    private static final String ATTR_PERIODICAL = "periodical";

    private PeriodicalIssueService periodicalIssueService = PeriodicalIssueService.getInstance();
    private SubscriptionService subscriptionService = SubscriptionService.getInstance();

    @Override
    public PaginationInfoHolder<PeriodicalIssue> getPaginationInfoHolderInstance(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User currentUser = AuthenticationHelper.getUserFromSession(session);

        if (Objects.nonNull(currentUser) && CommandUtils.paramClarifiedInQuery(request, ATTR_PERIODICAL)) {
            String periodicalIdParameter = request.getParameter(ATTR_PERIODICAL);
            if (UUIDHelper.isUUID(periodicalIdParameter)) {
                UUID periodicalId = UUID.fromString(periodicalIdParameter);
                if (subscriptionService.getIsUserSubscribedForPeriodical(currentUser.getId(), periodicalId)) {
                    return getPeriodicalIssuesPaginationInfoHolder(request, periodicalId);
                }
            }
        }
        return null;
    }

    @Override
    protected PageableCollectionService<PeriodicalIssue> getPageableCollectionService() {
        return periodicalIssueService;
    }

    @Override
    protected String getPageHrefTemplate() {
        return ADMIN_PERIODICAL_ISSUES;
    }

    private PaginationInfoHolder<PeriodicalIssue> getPeriodicalIssuesPaginationInfoHolder(HttpServletRequest request, UUID periodicalId) {
        PaginationInfoHolder<PeriodicalIssue> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = periodicalIssueService.getIssuesByPeriodicalCount(periodicalId);
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(this.getRecordsPerPage());

        List<PeriodicalIssue> displayedObjects = periodicalIssueService.getIssuesByPeriodicalListBounded
                (holder.getSkippedRecordsCount(), holder.getRecordsPerPage(), periodicalId);
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(this.getPageHrefTemplate() + "?periodical=" + periodicalId);

        return holder;
    }
}
