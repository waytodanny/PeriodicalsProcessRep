package com.periodicals.command.admin;

import com.periodicals.command.util.CommandUtils;
import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.services.PeriodicalIssueService;
import com.periodicals.services.PeriodicalService;
import com.periodicals.services.util.PageableCollectionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_PERIODICAL_ISSUE_EDIT_PAGE;

public class DisplayEditingIssuesCommand extends PagedCommand<PeriodicalIssue> {
    private static final int RECORDS_PER_PAGE = 10;

    private static final PeriodicalIssueService periodicalIssueService = PeriodicalIssueService.getInstance();
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    @Override
    protected PaginationInfoHolder<PeriodicalIssue> getPaginationInfoHolderInstance(HttpServletRequest request) {
        if (CommandUtils.paramClarifiedInQuery(request,"periodical")) {
            String periodicalId = request.getParameter("periodical");
            Periodical periodical = periodicalService.getPeriodicalById(periodicalId);
            if (Objects.nonNull(periodical)) {
                return getIssuesPaginationInfoHolder(request, periodical);
            }
        }
        return null;
    }

    @Override
    protected PageableCollectionService<PeriodicalIssue> getPageableCollectionService() {
        return null;
    }

    @Override
    protected int getRecordsPerPage() {
        return 0;
    }

    @Override
    protected String getPageHrefTemplate() {
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

        holder.setPageHrefTemplate(ADMIN_PERIODICAL_ISSUE_EDIT_PAGE + "?periodical=" + periodical.getId());

        return holder;
    }
}
