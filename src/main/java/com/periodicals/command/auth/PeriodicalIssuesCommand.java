package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.entities.User;
import com.periodicals.services.PeriodicalIssueService;
import com.periodicals.services.PeriodicalService;
import com.periodicals.services.UserSubscriptionsService;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.periodicals.command.util.CommandHelper.requiredFieldsNotEmpty;
import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.ATTR_USER;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ERROR_PAGE;
import static com.periodicals.utils.ResourceHolders.PagesHolder.PERIODICAL_ISSUES_PAGE;

public class PeriodicalIssuesCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(PeriodicalIssuesCommand.class.getSimpleName());

    private PeriodicalIssueService issueService = PeriodicalIssueService.getInstance();
    private PeriodicalService periodicalService = PeriodicalService.getInstance();
    private UserSubscriptionsService subsService = UserSubscriptionsService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        Locale locale = req.getLocale();

        if (AuthenticationHelper.isUserLoggedIn(req.getSession())) {
            String perId = req.getParameter("periodical");
            if (requiredFieldsNotEmpty(perId)) {
                User user = (User) req.getSession().getAttribute(ATTR_USER);
                try {
                    int perIdValue = Integer.parseInt(perId);
                    Periodical periodical = periodicalService.getPeriodicalById(perIdValue);
                    if (Objects.nonNull(periodical) && subsService.isSubscribed(user, periodical)) {
                        List<PeriodicalIssue> issues = issueService.getIssuesByPeriodical(periodical);
                        req.setAttribute("issues", issues);
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
}
