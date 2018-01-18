package com.periodicals.command.admin;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.Genre;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.Publisher;
import com.periodicals.services.GenresService;
import com.periodicals.services.PeriodicalService;
import com.periodicals.services.PublisherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_ADD_PERIODICAL_PAGE;

public class AddPeriodicalCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("add_name");
        String desc = req.getParameter("add_desc");
        String subCost = req.getParameter("add_sub_cost");
        String is_limited = req.getParameter("add_limited_options");
        String iss_per_year = req.getParameter("add_iss_per_year");
        String genreId = req.getParameter("add_genre_id");
        String publishId = req.getParameter("add_publish_id");

        String[] reqFields = {name, desc, subCost, is_limited, iss_per_year, genreId, publishId};
        if (CommandHelper.requiredFieldsNotEmpty(reqFields)) {
            Periodical newPer = new Periodical();
            newPer.setName(name);
            newPer.setDescription(desc);
            newPer.setLimited(Boolean.valueOf(is_limited));

            try {
                newPer.setSubscriptionCost(new BigDecimal(subCost));
                newPer.setIssuesPerYear(Short.parseShort(iss_per_year));
//                newPer.setGenreId(Short.parseShort(genreId));
//                newPer.setPublisherId(Integer.parseInt(publishId));

                PeriodicalService.getInstance().add(newPer);

                req.setAttribute("addingResultMessage", "Successful periodical adding");
            } catch (Exception e) {
                req.setAttribute("addingResultMessage", "Failed to add periodical: " + e.getMessage());
            }
        }

        createGenresList(req);
        createPublishersList(req);

        return new CommandResult(req, resp, FORWARD, ADMIN_ADD_PERIODICAL_PAGE);
    }

    /*POVTOR*/
    private void createGenresList(HttpServletRequest request) {
        List<Genre> genres = GenresService.getInstance().getAll();
        request.setAttribute("genres", genres);
    }

    /*POVTOR*/
    private void createPublishersList(HttpServletRequest request) {
        List<Publisher> publishers = PublisherService.getInstance().getAll();
        request.setAttribute("publishers", publishers);
    }
}
