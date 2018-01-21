package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.services.PeriodicalIssueService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class DeleteIssueCommand implements Command {
    private static final PeriodicalIssueService periodicalIssueService = PeriodicalIssueService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if(CommandUtils.isPostMethod(request)) {
            if (CommandUtils.paramClarifiedInQuery(request, "id")) {
                UUID id = UUID.fromString(request.getParameter("id"));

                try {
                /*PeriodicalIssue deleted = periodicalIssueService.getByPk(id);
                periodicalIssueService.delete(deleted);*/
                    request.setAttribute("resultMessage", "Successfully deleted issue");
                } catch (Exception e) {
                    request.setAttribute("resultMessage", "Failed to delete issue");
                }
            }
        }

        return null;
    }
}
