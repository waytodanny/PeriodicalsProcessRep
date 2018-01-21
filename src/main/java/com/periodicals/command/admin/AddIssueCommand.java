package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.entities.Periodical;
import com.periodicals.services.PeriodicalIssueService;
import com.periodicals.services.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_ADD_ISSUE_PAGE;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_DEFAULT_PAGE;

public class AddIssueCommand implements Command {
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();
    private static final PeriodicalIssueService periodicalIssueService = PeriodicalIssueService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if(CommandUtils.isPostMethod(request)) {
            String name = request.getParameter("name");
            String issueNo = request.getParameter("issue_no");
            String periodicalId = request.getParameter("periodical_id");

            Object[] requiredFields = {
                    name,
                    issueNo,
                    periodicalId
            };

            if (CommandUtils.requiredFieldsNotEmpty(requiredFields)) {
                try {
                    periodicalIssueService.addNewIssue(name, Integer.parseInt(issueNo), periodicalId);
                    request.setAttribute("resultMessage", "Successfully added issue");
                } catch (Exception e) {
                    request.setAttribute("resultMessage", "Failed to addNewIssue issue: " + e.getMessage());
                }
                return new CommandResult(FORWARD, ADMIN_DEFAULT_PAGE);
            } else {
                this.initializePageAttributes(request);
            }
        }
        return new CommandResult(FORWARD, ADMIN_ADD_ISSUE_PAGE);
    }

    private void initializePageAttributes (HttpServletRequest request) {
        if(CommandUtils.paramClarifiedInQuery(request,"periodical_id")) {
            String periodicalId = request.getParameter("periodical_id");
            Periodical periodical = periodicalService.getPeriodicalById(periodicalId);
            request.setAttribute("periodical_name", periodical.getName());
        }
    }
}
