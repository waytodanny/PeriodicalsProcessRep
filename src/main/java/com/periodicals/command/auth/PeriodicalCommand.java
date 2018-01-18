package com.periodicals.command.auth;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.Periodical;
import com.periodicals.services.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.periodicals.command.util.RedirectType.*;
import static com.periodicals.utils.ResourceHolders.PagesHolder.PERIODICAL_PAGE;


public class PeriodicalCommand implements Command {
    private PeriodicalService perService = PeriodicalService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        int periodicalId = 1;
        if (req.getParameter("id") != null) {
            periodicalId = Integer.parseInt(req.getParameter("id"));
        }

        Periodical periodical = perService.getPeriodicalById(periodicalId);

        req.setAttribute("periodical", periodical);
        return new CommandResult(req, resp, FORWARD, PERIODICAL_PAGE + "?id=" + periodicalId);
    }
}
