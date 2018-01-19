package com.periodicals.command.admin;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.services.PeriodicalIssueService;
import com.periodicals.services.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_PERIODICAL_ISSUE_EDIT_PAGE;

public class IssuesEditingCommand implements Command {
    private PeriodicalIssueService issueService = PeriodicalIssueService.getInstance();
    private PeriodicalService periodicalService = PeriodicalService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        if (isDeleteCommand(req)) {
            long id = Long.parseLong(req.getParameter("deletedIssueId"));
            PeriodicalIssue upToDelete = issueService.getByPk(id);
            try {
                issueService.delete(upToDelete);
            } catch (ServiceException e) {
                e.printStackTrace();
            }

        } else if (isUpdateCommand(req)) {
            int id = Integer.parseInt(req.getParameter("updatedIssueId"));
            String name = req.getParameter("updatedIssueName");
            String issueNo = req.getParameter("updatedIssueNo");

            String [] reqFields = {name, issueNo};
            if (CommandHelper.requiredFieldsNotEmpty(reqFields)) {
                PeriodicalIssue updIssue = PeriodicalIssueService.getInstance().getByPk(id);
                updIssue.setName(name);
                updIssue.setIssueNo(Integer.parseInt(issueNo));
                try {
                    PeriodicalIssueService.update(updIssue);
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        }

        int periodicalId = 1;
        if (req.getParameter("id") != null && !Objects.equals(req.getParameter("id"), "")) {
            periodicalId = Integer.parseInt(req.getParameter("id"));
        }

        Periodical periodical = periodicalService.getPeriodicalById(periodicalId);
        List<PeriodicalIssue> issues = issueService.getPeriodicalIssues(periodical);

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
