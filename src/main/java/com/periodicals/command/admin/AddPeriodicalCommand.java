package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.services.entities.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.UUID;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_ADD_PERIODICAL_PAGE;
import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_DEFAULT_PAGE;

/**
 * @author Daniel Volnitsky
 *
 * Admin command that is responsible for creating new Periodical object
 *
 * @see com.periodicals.entities.Periodical
 */
public class AddPeriodicalCommand implements Command {
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if (CommandUtils.isPostMethod(request)) {
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String subscriptionCost = request.getParameter("subscription_cost");
            String isLimited = request.getParameter("is_limited");
            String issuesPerYear = request.getParameter("issues_per_year");
            String genreId = request.getParameter("genre_id");
            String publisherId = request.getParameter("publisher_id");

            String[] requiredFields = {
                    name,
                    description,
                    subscriptionCost,
                    isLimited,
                    issuesPerYear,
                    genreId,
                    publisherId
            };

            if (CommandUtils.requiredFieldsNotEmpty(requiredFields)) {
                try {
                    periodicalService.createEntity(
                            name,
                            description,
                            new BigDecimal(subscriptionCost),
                            Boolean.valueOf(isLimited),
                            Short.parseShort(issuesPerYear),
                            UUID.fromString(genreId),
                            UUID.fromString(publisherId)
                    );
                    request.setAttribute("resultMessage", "Successfully added periodical");
                } catch (Exception e) {
                    request.setAttribute("resultMessage", "Failed to add periodical: " + e.getMessage());
                }
                return new CommandResult(FORWARD, ADMIN_DEFAULT_PAGE);
            }
        }
        return new CommandResult(FORWARD, ADMIN_ADD_PERIODICAL_PAGE);
    }
}
