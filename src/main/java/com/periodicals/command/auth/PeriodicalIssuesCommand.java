package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.dto.UserDto;
import com.periodicals.dao.entities.Periodical;
import com.periodicals.dao.entities.PeriodicalIssue;
import com.periodicals.services.PeriodicalIssueService;
import com.periodicals.services.PeriodicalService;
import com.periodicals.services.UserSubscriptionsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.PagesHolder.ERROR_PAGE;
import static com.periodicals.utils.PagesHolder.PERIODICAL_ISSUES_PAGE;

public class PeriodicalIssuesCommand implements Command {
    private PeriodicalIssueService issueService = PeriodicalIssueService.getInstance();
    private UserSubscriptionsService subsService = UserSubscriptionsService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {

        if (!AuthenticationHelper.isUserLoggedIn(req.getSession())) {
            return new CommandResult(req, resp, FORWARD, ERROR_PAGE);
        }

        String perId = req.getParameter("periodical");
        if (Objects.isNull(perId) || isWrongPeriodicalId(perId)) {
            return new CommandResult(req, resp, FORWARD, ERROR_PAGE);
        }

        UserDto user = (UserDto) req.getSession().getAttribute("user");
        Periodical per = PeriodicalService.getInstance().getPeriodicalById(Integer.parseInt(perId));
        if (subsService.isSubscribed(user.getUuid(), per)) {
            List<PeriodicalIssue> issues = issueService.getIssuesByPeriodicalId(per.getId());
            req.setAttribute("issues", issues);
            return new CommandResult(req, resp, FORWARD, PERIODICAL_ISSUES_PAGE);
        } else {
            return new CommandResult(req, resp, FORWARD, ERROR_PAGE);
        }
    }

    private boolean isWrongPeriodicalId(String perId) {
        boolean wrong = true;
        try {
            int id = Integer.parseInt(perId);
            wrong = false;
        } catch (Exception e) {
            /*TODO log*/
        }
        return wrong;
    }

    private boolean isSubscribed(Periodical per, List<Periodical> subs) {
        return subs.contains(per);
    }
}
