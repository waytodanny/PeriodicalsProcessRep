package com.periodicals.command.admin;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.services.PeriodicalIssueService;
import com.periodicals.services.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_ADD_ISSUE_PAGE;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_MAIN_PAGE;

public class AddIssueCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("add_name");
        String no = req.getParameter("add_no");
        String perId = req.getParameter("id");

        if (Objects.nonNull(perId)) {
            Periodical per = PeriodicalService.getInstance().getPeriodicalById(Integer.parseInt(perId));
            req.setAttribute("periodical_name", per.getName());

            String[] requiredFields = {name, no};
            if (CommandHelper.requiredFieldsNotEmpty(requiredFields)) {
                PeriodicalIssue newIssue = new PeriodicalIssue();
                newIssue.setName(name);
                newIssue.setIssueNo(Integer.parseInt(no));
                try {
                    newIssue.setPeriodicalId(Integer.parseInt(perId));
                    PeriodicalIssueService.getInstance().add(newIssue);

                    req.setAttribute("addingResultMessage", "Successful issue adding");
                } catch (Exception e) {
                    req.setAttribute("addingResultMessage", "Failed to add issue: " + e.getMessage());
                }
            }
            return new CommandResult(req, resp, FORWARD, ADMIN_ADD_ISSUE_PAGE);
        }
        return new CommandResult(req, resp, FORWARD, ADMIN_MAIN_PAGE);
    }
}
