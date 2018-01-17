package com.periodicals.command.auth;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;

import com.periodicals.entities.Genre;
import com.periodicals.entities.Periodical;
import com.periodicals.services.GenresService;
import com.periodicals.services.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.periodicals.command.util.CommandHelper.paramClarifiedInQuery;
import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.PagesHolder.CATALOG_PAGE;

public class CatalogCommand implements Command {
    protected static final int RECORDS_PER_PAGE = 10;

    private PeriodicalService perService = PeriodicalService.getInstance();
    private GenresService genresService = GenresService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        PeriodicalsRequestData data = getPeriodicalsRequestData(req);
        SetRequestAttributes(req, data);
        return new CommandResult(req, resp, FORWARD, data.pageLink);
    }

    protected PeriodicalsRequestData getPeriodicalsRequestData(HttpServletRequest req) {
        Genre genre = null;
        if (paramClarifiedInQuery(req, "genre")) {
            genre = genresService.getGenre(req.getParameter("genre"));
        }
        if (genre != null) {
            return getPeriodicalsRequestDataByGenre(req, genre);
        }
        return getPeriodicalsRequestDataAll(req);
    }

    /**
     * fills PeriodicalsRequestData object by sublist of common periodicals
     *
     * @see PeriodicalsRequestData
     */
    private PeriodicalsRequestData getPeriodicalsRequestDataAll(HttpServletRequest req) {
        PeriodicalsRequestData data = new PeriodicalsRequestData();
        data.currentPage = getPageFromRequest(req);
        data.periodicals = perService.getPeriodicalsSublist
                ((data.currentPage - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        data.recordsCount = perService.getPeriodicalsCount();
        data.pageLink = CATALOG_PAGE + "?page=" + data.currentPage;
        return data;
    }

    /**
     * fills PeriodicalsRequestData object by sublist of some genre periodicals
     *
     * @see PeriodicalsRequestData
     */
    private PeriodicalsRequestData getPeriodicalsRequestDataByGenre(HttpServletRequest req, Genre genre) {
        PeriodicalsRequestData data = new PeriodicalsRequestData();
        data.currentPage = getPageFromRequest(req);
        data.periodicals = perService.getGenrePeriodicalsSublist
                (genre, (data.currentPage - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        data.recordsCount = perService.getGenrePeriodicalCount(genre);
        data.pageLink = CATALOG_PAGE + "?genre=" + genre.getName() + "?page=" + data.currentPage;
        return data;
    }

    /**
     * Gets page from request query or returns default if page param is not found
     */
    protected int getPageFromRequest(HttpServletRequest req) {
        int page = 1;
        if (paramClarifiedInQuery(req, "page")) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        return page;
    }

    /**
     * Sets request attributes that are needed for view to display
     */
    private void SetRequestAttributes(HttpServletRequest req, PeriodicalsRequestData data) {
        List<Genre> genres = genresService.getAll();
        int pagesCount = CommandHelper.getPagesCount(data.recordsCount, RECORDS_PER_PAGE);

        req.setAttribute("genres", genres);
        req.setAttribute("periodicals", data.periodicals);
        req.setAttribute("pagesCount", pagesCount);
        req.setAttribute("currentPage", data.currentPage);
    }

    /**
     * Additional class that is needed to carry some info for pages that use periodicals pagination
     */
    class PeriodicalsRequestData {
        protected List<Periodical> periodicals;
        protected long recordsCount;
        protected int currentPage;
        protected String pageLink;
    }
}