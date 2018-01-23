package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.services.entities.PeriodicalService;
import com.periodicals.utils.uuid.UUIDHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 *
 * Admin command that is responsible for providing periodical service with
 * information about deleted periodical
 *
 * @see com.periodicals.entities.Periodical
 * @see PeriodicalService
 */
public class DeletePeriodicalCommand implements Command {
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if (CommandUtils.isPostMethod(request)) {
            if (CommandUtils.paramClarifiedInQuery(request, "id")) {
                String periodicalIdParam = request.getParameter("id");
                if(UUIDHelper.checkIsUUID(periodicalIdParam)){
                    try {
                        periodicalService.deleteEntity(
                                UUID.fromString(periodicalIdParam)
                        );
                        request.setAttribute("resultMessage", "Successfully deleted periodical");
                    } catch (Exception e) {
                        request.setAttribute("resultMessage", "Failed to delete periodical: " + e.getMessage());
                    }
                }
            }
        }
        return null;
    }
}
