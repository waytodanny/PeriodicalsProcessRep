package com.periodicals.command.admin;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.services.PeriodicalIssueService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.PagesHolder.ADMIN_PERIODICAL_ISSUE_EDIT_PAGE;

public class IssuesEditingCommand implements Command {
    private PeriodicalIssueService issueService = PeriodicalIssueService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        if (isDeleteCommand(req)) {
            int id = Integer.parseInt(req.getParameter("deletedIssueId"));
            issueService.delete(id);

        } else if (isUpdateCommand(req)) {
            int id = Integer.parseInt(req.getParameter("updatedIssueId"));
            String name = req.getParameter("updatedIssueName");
            String issueNo = req.getParameter("updatedIssueNo");

            String [] reqFields = {name, issueNo};
            if (CommandHelper.requiredFieldsNotEmpty(reqFields)) {
                PeriodicalIssue updIssue = PeriodicalIssueService.getInstance().getByPk(id);
                updIssue.setName(name);
                updIssue.setIssueNo(Integer.parseInt(issueNo));
                PeriodicalIssueService.update(updIssue);
            }
        }

        int periodicalId = 1;
        if (req.getParameter("id") != null && !Objects.equals(req.getParameter("id"), "")) {
            periodicalId = Integer.parseInt(req.getParameter("id"));
        }

        List<PeriodicalIssue> issues = issueService.getByPeriodicalId(periodicalId);

        req.setAttribute("issues", issues);
        return new CommandResult(req, resp, FORWARD, ADMIN_PERIODICAL_ISSUE_EDIT_PAGE + "?id=" + periodicalId);
    }

    /*POVTOP*/
    private boolean isDeleteCommand(HttpServletRequest request) {
        String parameter = request.getParameter("deletedIssueId");
        return CommandHelper.requiredFieldsNotEmpty(new String[]{parameter});
    }

    /*POVTOP*/
    private boolean isUpdateCommand(HttpServletRequest request) {
        String parameter = request.getParameter("updatedIssueId");
        return CommandHelper.requiredFieldsNotEmpty(new String[]{parameter});
    }
}
