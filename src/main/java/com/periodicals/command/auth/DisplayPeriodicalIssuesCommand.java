package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.entities.User;
import com.periodicals.services.entity.PeriodicalIssueService;
import com.periodicals.services.entity.SubscriptionsService;
import com.periodicals.services.util.PageableCollectionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.periodicals.utils.ResourceHolders.AttributesHolder.ATTR_PERIODICAL;
import static com.periodicals.utils.ResourceHolders.PagesHolder.PERIODICAL_ISSUES_PAGE;

public class DisplayPeriodicalIssuesCommand extends PagedCommand<PeriodicalIssue> {
    private static final int RECORDS_PER_PAGE = 10;

    private PeriodicalIssueService periodicalIssueService = PeriodicalIssueService.getInstance();
    private SubscriptionsService subscriptionsService = SubscriptionsService.getInstance();

    @Override
    public PaginationInfoHolder<PeriodicalIssue> getPaginationInfoHolderInstance(HttpServletRequest request) {
        User currentUser = AuthenticationHelper.getUserFromSession(request.getSession());

        if (Objects.nonNull(currentUser) && CommandUtils.paramClarifiedInQuery(request, ATTR_PERIODICAL)) {
            UUID periodicalId = UUID.fromString(request.getParameter(ATTR_PERIODICAL));
            if (subscriptionsService.isSubscribed(currentUser, periodicalId)) {
                return getPeriodicalIssuesPaginationInfoHolder(request, periodicalId);
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
        return null;
    }

    private PaginationInfoHolder<PeriodicalIssue> getPeriodicalIssuesPaginationInfoHolder(HttpServletRequest request, UUID periodicalId) {
        PaginationInfoHolder<PeriodicalIssue> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = periodicalIssueService.getPeriodicalIssuesCount(periodicalId);
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(RECORDS_PER_PAGE);

        List<PeriodicalIssue> displayedObjects = periodicalIssueService.getPeriodicalIssuesLimited
                (periodicalId, holder.getSkippedRecordsCount(), holder.getRecordsPerPage());
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(PERIODICAL_ISSUES_PAGE + "?periodical=" + periodicalId);

        return holder;
    }
}
