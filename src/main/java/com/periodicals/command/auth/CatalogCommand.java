package com.periodicals.command.auth;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.Genre;
import com.periodicals.entities.Periodical;
import com.periodicals.services.GenresService;
import com.periodicals.services.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.periodicals.command.util.CommandHelper.getPageFromRequest;
import static com.periodicals.command.util.CommandHelper.paramClarifiedInQuery;
import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.ResourceHolders.PagesHolder.CATALOG_PAGE;

public class CatalogCommand implements Command, PagedCommand {
    private static final int RECORDS_PER_PAGE = 10;

    private PeriodicalService perService = PeriodicalService.getInstance();
    private GenresService genresService = GenresService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        PaginationInfoHolder<Periodical, Integer> holder = getPaginationInfo(req);
        setPaginationRequestAttributes(req, holder);
        return new CommandResult(req, resp, FORWARD, holder.getRedirectedPageLink());
    }

    public PaginationInfoHolder<Periodical, Integer> getPaginationInfo(HttpServletRequest req) {
        Genre genre = null;
        if (paramClarifiedInQuery(req, "genre")) {
            genre = genresService.getGenre(req.getParameter("genre"));
        }
        if (genre != null) {
            return getGenrePaginationInfo(req, genre);
        }
        return getPeriodicalsPaginationInfo(req);
    }

    private void setPaginationRequestAttributes(HttpServletRequest req, PaginationInfoHolder<Periodical, Integer> holder) {
        int pagesCount = CommandHelper.getPagesCount(holder.getRecordsCount(), RECORDS_PER_PAGE);

        req.setAttribute("periodicals", holder.getDisplayedObjects());
        req.setAttribute("pagesCount", pagesCount);
        req.setAttribute("currentPage", holder.getCurrentPage());
        req.setAttribute("pageLink", holder.getNextPageHrefLink());
    }

    protected PaginationInfoHolder<Periodical, Integer> getPeriodicalsPaginationInfo(HttpServletRequest req) {
        PaginationInfoHolder<Periodical, Integer> holder = new PaginationInfoHolder<>();
        int currentPage = getPageFromRequest(req);
        holder.setCurrentPage(currentPage);

        int recordsCount = Math.toIntExact(perService.getPeriodicalsCount());
        holder.setRecordsCount(recordsCount);

        List<Periodical> displayedSublist = perService.getPeriodicalsSublist
                ((currentPage - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        holder.setDisplayedObjects(displayedSublist);

        holder.setRedirectedPageLink(CATALOG_PAGE + "?page=" + currentPage);
        holder.setNextPageHrefLink(CATALOG_PAGE/* + "?page=" + currentPage + 1*/);

        return holder;
    }

    /**
     * fills PaginationInfoHolder object by sublist of some genre periodicals
     *
     * @see
     */
    private PaginationInfoHolder<Periodical, Integer> getGenrePaginationInfo(HttpServletRequest req, Genre genre) {
        PaginationInfoHolder<Periodical, Integer> holder = new PaginationInfoHolder<>();
        int currentPage = getPageFromRequest(req);
        holder.setCurrentPage(currentPage);

        int recordsCount = Math.toIntExact(perService.getGenrePeriodicalsCount(genre));
        holder.setRecordsCount(recordsCount);

        List<Periodical> displayedSublist = perService.getGenrePeriodicalsSublist
                (genre, (currentPage - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        holder.setDisplayedObjects(displayedSublist);

        holder.setRedirectedPageLink(CATALOG_PAGE + "?genre=" + genre.getName() + "?page=" + currentPage);
        holder.setNextPageHrefLink(CATALOG_PAGE + "?genre=" + genre.getName()/* + "?page=" + currentPage + 1*/);

        return holder;
    }
}