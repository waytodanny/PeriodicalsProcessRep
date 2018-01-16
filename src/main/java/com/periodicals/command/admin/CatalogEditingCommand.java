package com.periodicals.command.admin;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.dto.PeriodicalDto;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.Publisher;
import com.periodicals.services.PeriodicalService;
import com.periodicals.services.PublisherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.PagesHolder.ADMIN_PERIODICALS_EDIT_PAGE;

public class CatalogEditingCommand implements Command {
    private PeriodicalService perService = PeriodicalService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        if (isDeleteCommand(req)) {
            int id = Integer.parseInt(req.getParameter("deleteId"));
            perService.delete(id);

        } else if (isUpdateCommand(req)) {
            int id = Integer.parseInt(req.getParameter("updateId"));

            Periodical upToEdit = PeriodicalService.getInstance().getPeriodicalById(id);
            upToEdit.setName(req.getParameter("updatedName"));
            upToEdit.setDescription(req.getParameter("updatedDesc"));
            upToEdit.setSubscriptionCost(new BigDecimal(req.getParameter("updatedSubscrCost")));
            upToEdit.setIssuesPerYear(Short.parseShort(req.getParameter("updatedIssuesPerYear")));
            upToEdit.setLimited(Boolean.valueOf(req.getParameter("updatedIsLimited")));

            if (isGenreChanged(upToEdit.getGenreId(), req)) {
                short newGenreId = Short.parseShort(req.getParameter("genreId"));
                upToEdit.setGenreId(newGenreId);
            }

            if (isPublisherChanged(upToEdit.getPublisherId(), req)) {
                int newPublId = Integer.parseInt(req.getParameter("publisherId"));
                upToEdit.setPublisherId(newPublId);
            }

            PeriodicalService.getInstance().update(upToEdit);
        }

        createGenresList(req);
        createPublishersList(req);

        int page = 1;
        int recordsPerPage = 5;
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));

        int entriesCount = PeriodicalService.getInstance().getPeriodicalsCount();
        int pagesCount = (int) Math.ceil(entriesCount * 1.0 / recordsPerPage);

        int skip = (page - 1) * recordsPerPage;
        List<PeriodicalDto> periodicals = PeriodicalService.getInstance().
                getPeriodicalsDtoSublist(skip, recordsPerPage);

        req.setAttribute("periodicals", periodicals);
        req.setAttribute("pagesCount", pagesCount);
        req.setAttribute("currentPage", page);

        return new CommandResult(req, resp, FORWARD, ADMIN_PERIODICALS_EDIT_PAGE + "?page=" + page);
    }

    private boolean isPublisherChanged(int oldId, HttpServletRequest request) {
        String selectedId = request.getParameter("publisherId");
        return CommandHelper.paramIsNotEmpty(selectedId)
                && !Objects.equals(oldId, Integer.parseInt(selectedId));
    }

    private boolean isGenreChanged(short olId, HttpServletRequest request) {
        String selectedId = request.getParameter("genreId");
        return CommandHelper.paramIsNotEmpty(selectedId)
                && !Objects.equals(olId, Short.parseShort(selectedId));
    }

    private boolean isDeleteCommand(HttpServletRequest request) {
        String parameter = request.getParameter("deleteId");
        return CommandHelper.paramIsNotEmpty(parameter);
    }

    private boolean isUpdateCommand(HttpServletRequest request) {
        String parameter = request.getParameter("updateId");
        return CommandHelper.paramIsNotEmpty(parameter);
    }

    /*POVTOR*/
    private void createGenresList(HttpServletRequest request) {
//        List<Genre> genres = GenresService.getInstance().getAll();
//        request.setAttribute("genres", genres);
//        request.setAttribute("genres_count", genres.size() - 1);
    }

    /*POVTOR*/
    private void createPublishersList(HttpServletRequest request) {
        List<Publisher> publishers = PublisherService.getInstance().getAll();
        request.setAttribute("publishers", publishers);
        request.setAttribute("publishers_indexed_count", publishers.size() - 1);
    }
}
