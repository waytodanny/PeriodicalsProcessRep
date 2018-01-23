package com.periodicals.command.admin;

import com.periodicals.command.util.CommandUtils;
import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.services.entities.PeriodicalIssueService;
import com.periodicals.services.interfaces.PageableCollectionService;
import com.periodicals.utils.uuid.UUIDHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_PERIODICAL_ISSUE_EDIT_PAGE;

/**
 * @author Daniel Volnitsky
 *
 * Admin command that is responsible for displaying periodical issues limited lists
 *
 * @see com.periodicals.entities.PeriodicalIssue
 */
public class AdminIssuesCommand extends PagedCommand<PeriodicalIssue> {
    private static final PeriodicalIssueService periodicalIssueService = PeriodicalIssueService.getInstance();

    @Override
    protected PaginationInfoHolder<PeriodicalIssue> getPaginationInfoHolderInstance(HttpServletRequest request) {
        if (CommandUtils.paramClarifiedInQuery(request, "periodical")) {
            String periodicalIdParam = request.getParameter("periodical");
            if (UUIDHelper.checkIsUUID(periodicalIdParam)) {
                UUID periodicalId = UUID.fromString(periodicalIdParam);
                return getIssuesPaginationInfoHolder(request, periodicalId);
            }
        }
        return null;
    }

    @Override
    protected PageableCollectionService<PeriodicalIssue> getPageableCollectionService() {
        return null;
    }


    @Override
    protected String getPageHrefTemplate() {
        return ADMIN_PERIODICAL_ISSUE_EDIT_PAGE;
    }

    private PaginationInfoHolder<PeriodicalIssue> getIssuesPaginationInfoHolder(HttpServletRequest request, UUID periodicalId) {
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
