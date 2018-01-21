package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.services.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class DeletePeriodicalCommand implements Command {
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if(CommandUtils.isPostMethod(request)) {
            if (CommandUtils.paramClarifiedInQuery(request, "id")) {
                UUID id = UUID.fromString(request.getParameter("id"));

                try {
                /*Periodical deleted = periodicalService.getPeriodicalById(id);
                periodicalService.delete(deleted);*/
                    request.setAttribute("resultMessage", "Successfully deleted periodical");
                } catch (Exception e) {
                    request.setAttribute("resultMessage", "Failed to delete periodical");
                }
            }
        }
        return null;
    }
}
