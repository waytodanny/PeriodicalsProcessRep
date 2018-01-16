package com.periodicals.command.auth;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.dto.GenreDto;
import com.periodicals.dto.PeriodicalDto;
import com.periodicals.entities.Genre;
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
         /*Cюда заходит с команты user subscription где не нужны жанры*/
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

    private PeriodicalsRequestData getPeriodicalsRequestDataAll(HttpServletRequest req) {
        PeriodicalsRequestData data = new PeriodicalsRequestData();
        data.currentPage = getPageFromReguest(req);
        data.periodicals = perService.getPeriodicalsDtoSublist
                ((data.currentPage - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        data.recordsCount = perService.getPeriodicalsCount();
        data.pageLink = CATALOG_PAGE + "?page=" + data.currentPage;
        return data;
    }

    private PeriodicalsRequestData getPeriodicalsRequestDataByGenre(HttpServletRequest req, Genre genre) {
        PeriodicalsRequestData data = new PeriodicalsRequestData();
        data.currentPage = getPageFromReguest(req);
        data.periodicals = perService.getGenrePeriodicalsDtoSublist
                (genre, (data.currentPage - 1) * RECORDS_PER_PAGE, RECORDS_PER_PAGE);
        data.recordsCount = perService.getGenrePeriodicalCount(genre);
        data.pageLink = CATALOG_PAGE + "?genre=" + genre.getName() + "?page=" + data.currentPage;
        return data;
    }

    protected int getPageFromReguest(HttpServletRequest req) {
        int page = 1;
        if (paramClarifiedInQuery(req, "page")) {
            page = Integer.parseInt(req.getParameter("page"));
        }
        return page;
    }

    private void SetRequestAttributes(HttpServletRequest req, PeriodicalsRequestData data) {
        List<GenreDto> genres = genresService.getAll();
        int pagesCount = CommandHelper.getPagesCount(data.recordsCount, RECORDS_PER_PAGE);

        req.setAttribute("genres", genres);
        req.setAttribute("periodicals", data.periodicals);
        req.setAttribute("pagesCount", pagesCount);
        req.setAttribute("currentPage", data.currentPage);
    }

    class PeriodicalsRequestData {
        protected List<PeriodicalDto> periodicals;
        protected int recordsCount;
        protected int currentPage;
        protected String pageLink;
    }
}