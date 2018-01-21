package com.periodicals.command.admin;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.services.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_ADD_PERIODICAL_PAGE;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_DEFAULT_PAGE;

public class EditPeriodicalCommand implements Command {
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if (CommandUtils.paramClarifiedInQuery(request,"id")) {
            UUID id = UUID.fromString(request.getParameter("id"));
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
                    /*Periodical upToEdit = perService.getPeriodicalById(id);
                    upToEdit.setName(name);
                    upToEdit.setDescription(description);
                    upToEdit.setSubscriptionCost(new BigDecimal(subscriptionCost));
                    upToEdit.setIssuesPerYear(Short.parseShort(issuesPerYear));
                    upToEdit.setLimited(Boolean.valueOf(isLimited));

                    if (isGenreChanged(upToEdit.getGenre(), req)) {
                        short newGenreId = Short.parseShort(req.getParameter("genreId"));
                        Genre newGenre = genresService.getGenreById(newGenreId);
                        upToEdit.setGenre(newGenre);
                    }

                    if (isPublisherChanged(upToEdit.getPublisher(), req)) {
                        int newPublId = Integer.parseInt(req.getParameter("publisherId"));
                        Publisher newPublisher = publisherService.getPublisherById(newPublId);
                        upToEdit.setPublisher(newPublisher);
                    }
                    periodicalService.update(upToEdit);*/
                    request.setAttribute("resultMessage", "Successfully changed periodical info");
                } catch (Exception e) {
                    request.setAttribute("resultMessage", "Failed to modify periodical");
                }
                return new CommandResult(FORWARD, ADMIN_DEFAULT_PAGE);
            }
        }

        return new CommandResult(FORWARD, ADMIN_ADD_PERIODICAL_PAGE);
    }
}
