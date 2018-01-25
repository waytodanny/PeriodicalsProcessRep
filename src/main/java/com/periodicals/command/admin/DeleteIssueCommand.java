package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.services.entities.PeriodicalIssueService;
import com.periodicals.utils.uuid.UUIDHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 *
 * Admin command that is responsible for providing periodicalIssue service with
 * information about deleted periodical issue
 *
 * @see com.periodicals.entities.PeriodicalIssue
 * @see PeriodicalIssueService
 */
public class DeleteIssueCommand implements Command {
    private static final PeriodicalIssueService periodicalIssueService = PeriodicalIssueService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if (CommandUtils.isPostMethod(request)) {
            if (CommandUtils.paramClarifiedInQuery(request, "id")) {
                String issueIdParam = request.getParameter("id");
                if (UUIDHelper.isUUID(issueIdParam)) {
                    try {
                        periodicalIssueService.deleteEntity(
                                UUID.fromString(issueIdParam)
                        );
                        request.setAttribute("resultMessage", "Successfully deleted issue");
                    } catch (Exception e) {
                        request.setAttribute("resultMessage", "Failed to delete issue: " + e.getMessage());
                    }
                }
            }
        }
        return null;
    }
}
