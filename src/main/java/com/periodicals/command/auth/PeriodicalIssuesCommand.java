package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.Genre;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.entities.User;
import com.periodicals.services.GenresService;
import com.periodicals.services.PeriodicalIssueService;
import com.periodicals.services.PeriodicalService;
import com.periodicals.services.UserSubscriptionsService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

import static com.periodicals.command.util.CommandHelper.getPageFromRequest;
import static com.periodicals.command.util.CommandHelper.requiredFieldsNotEmpty;
import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.ATTR_USER;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ERROR_PAGE;
import static com.periodicals.utils.ResourceHolders.PagesHolder.PERIODICAL_ISSUES_PAGE;

public class PeriodicalIssuesCommand implements Command, PagedCommand {
    private static final Logger LOGGER = Logger.getLogger(PeriodicalIssuesCommand.class.getSimpleName());

    private static final int RECORDS_PER_PAGE = 10;

    private PeriodicalIssueService issueService = PeriodicalIssueService.getInstance();
    private PeriodicalService periodicalService = PeriodicalService.getInstance();
    private GenresService genresService = GenresService.getInstance();
    private UserSubscriptionsService subsService = UserSubscriptionsService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
//        Locale locale = req.getLocale();

        if (AuthenticationHelper.isUserLoggedIn(req.getSession())) {
            String perId = req.getParameter("periodical");
            if (requiredFieldsNotEmpty(perId)) {
                User user = (User) req.getSession().getAttribute(ATTR_USER);
                try {
                    int perIdValue = Integer.parseInt(perId);
                    Periodical periodical = periodicalService.getPeriodicalById(perIdValue);
                    if (Objects.nonNull(periodical) && subsService.isSubscribed(user, periodical)) {

                        /*TODO think of where would be better to do it*/
                        List<Genre> genres = genresService.getAll();
                        req.setAttribute("genres", genres);
                        /*TODO*/

                        PaginationInfoHolder<PeriodicalIssue, Long> holder = getPaginationInfo(req, periodical);
                        setPaginationRequestAttributes(req, holder);
                        return new CommandResult(req, resp, FORWARD, PERIODICAL_ISSUES_PAGE);
                    }
                } catch (Exception e) {
                    LOGGER.debug("failed to obtain user's issues: " + e.getMessage());
                }
            }
        }
//        req.setAttribute(ERROR_PAGE_MESSAGE,
//                LanguagePropsManager.getProperty("issues.error.not_purchased", locale));
        return new CommandResult(req, resp, FORWARD, ERROR_PAGE);
    }

    private PaginationInfoHolder<PeriodicalIssue, Long> getPaginationInfo(HttpServletRequest req, Periodical periodical) {
        PaginationInfoHolder<PeriodicalIssue, Long> holder = new PaginationInfoHolder<>();
        long currentPage = getPageFromRequest(req);
        holder.setCurrentPage(currentPage);

        long recordsCount = issueService.getPeriodicalIssuesCount(periodical);
        holder.setRecordsCount(recordsCount);

        List<PeriodicalIssue> displayedSublist = issueService.getPeriodicalIssuesLimited
                (periodical, (currentPage - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        holder.setDisplayedObjects(displayedSublist);

        holder.setRedirectedPageLink(PERIODICAL_ISSUES_PAGE + "?page=" + currentPage);
        holder.setNextPageHrefLink(PERIODICAL_ISSUES_PAGE/* + "?page=" + currentPage + 1*/);

        return holder;
    }

    private void setPaginationRequestAttributes(HttpServletRequest req, PaginationInfoHolder<PeriodicalIssue, Long> holder) {
        long pagesCount = CommandHelper.getPagesCount(holder.getRecordsCount(), RECORDS_PER_PAGE);

        req.setAttribute("issues", holder.getDisplayedObjects());
        req.setAttribute("pagesCount", pagesCount);
        req.setAttribute("currentPage", holder.getCurrentPage());
        req.setAttribute("pageLink", holder.getNextPageHrefLink());
    }
}
