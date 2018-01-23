package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.entities.Periodical;
import com.periodicals.services.entities.PeriodicalIssueService;
import com.periodicals.services.entities.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_ADD_ISSUE_PAGE;
import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_DEFAULT_PAGE;

/**
 * @author Daniel Volnitsky
 *
 * Admin command that is responsible for providing periodicals issues service with
 * information about updated periodical's issue
 *
 * @see com.periodicals.entities.PeriodicalIssue
 * @see PeriodicalIssueService
 */
public class EditIssueCommand implements Command {
    private static PeriodicalService periodicalService = PeriodicalService.getInstance();
    private static PeriodicalIssueService periodicalIssueService = PeriodicalIssueService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if (CommandUtils.isPostMethod(request)) {
            if (CommandUtils.paramClarifiedInQuery(request, "id")) {
                String id = request.getParameter("id");
                String name = request.getParameter("name");
                String issueNo = request.getParameter("issue_no");

                Object[] requiredFields = {
                        id,
                        name,
                        issueNo
                };

                if (CommandUtils.requiredFieldsNotEmpty(requiredFields)) {
                    try {
                        periodicalIssueService.updateEntity(
                                UUID.fromString(id),
                                name,
                                Integer.parseUnsignedInt(issueNo)
                        );
                        request.setAttribute("resultMessage", "Successfully updated issue");
                    } catch (Exception e) {
                        request.setAttribute("resultMessage", "Failed to update issue: " + e.getMessage());
                    }
                    return new CommandResult(FORWARD, ADMIN_DEFAULT_PAGE);
                } else {
                    this.initializePageAttributes(request);
                }
            }
        }
        return new CommandResult(FORWARD, ADMIN_ADD_ISSUE_PAGE);
    }

    /**
     * provides request with needed for view layer attributes
     */
    private void initializePageAttributes(HttpServletRequest request) {
        if (CommandUtils.paramClarifiedInQuery(request, "periodical")) {
            UUID periodicalId = UUID.fromString(request.getParameter("periodical"));
            Periodical periodical = periodicalService.getEntityByPrimaryKey(periodicalId);
            request.setAttribute("periodical_name", periodical.getName());
        }
    }
}
