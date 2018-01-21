package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.entities.User;
import com.periodicals.services.PeriodicalIssueService;
import com.periodicals.services.PeriodicalService;
import com.periodicals.services.SubscriptionsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.periodicals.utils.ResourceHolders.AttributesHolder.ATTR_PERIODICAL;
import static com.periodicals.utils.ResourceHolders.PagesHolder.PERIODICAL_ISSUES_PAGE;

public class PeriodicalIssuesDisplayCommand extends PagedCommand<PeriodicalIssue> {
    private static final int RECORDS_PER_PAGE = 10;

    private PeriodicalService periodicalService = PeriodicalService.getInstance();
    private PeriodicalIssueService periodicalIssueService = PeriodicalIssueService.getInstance();
    private SubscriptionsService subscriptionsService = SubscriptionsService.getInstance();

    @Override
    public PaginationInfoHolder<PeriodicalIssue> getPaginationInfoHolderInstance(HttpServletRequest request) {
        User currentUser = AuthenticationHelper.getUserFromSession(request.getSession());

        if (Objects.nonNull(currentUser) && CommandUtils.paramClarifiedInQuery(request,ATTR_PERIODICAL)) {
            String periodicalId = request.getParameter(ATTR_PERIODICAL);
            Periodical periodical = periodicalService.getPeriodicalById(periodicalId);
            if (Objects.nonNull(periodical) && subscriptionsService.isSubscribed(currentUser, periodical)) {
                return getIssuesPaginationInfoHolder(request, periodical);
            }
        }
        return null;
    }

    private PaginationInfoHolder<PeriodicalIssue> getIssuesPaginationInfoHolder(HttpServletRequest request, Periodical periodical) {
        PaginationInfoHolder<PeriodicalIssue> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = Math.toIntExact(periodicalIssueService.getPeriodicalIssuesCount(periodical));
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(RECORDS_PER_PAGE);

        List<PeriodicalIssue> displayedObjects = periodicalIssueService.getPeriodicalIssuesLimited
                (periodical, holder.getSkippedRecordsCount(), holder.getRecordsPerPage());
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(PERIODICAL_ISSUES_PAGE + "?periodical=" + periodical.getId());

        return holder;
    }
}
