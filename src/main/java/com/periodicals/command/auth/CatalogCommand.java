package com.periodicals.command.auth;

import com.periodicals.command.util.CommandUtils;
import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.Genre;
import com.periodicals.entities.Periodical;
import com.periodicals.services.GenresService;
import com.periodicals.services.PeriodicalService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.periodicals.utils.ResourceHolders.AttributesHolder.ATTR_GENRE;
import static com.periodicals.utils.ResourceHolders.PagesHolder.CATALOG_PAGE;

public class CatalogCommand extends PagedCommand<Periodical> {
    private static final int RECORDS_PER_PAGE = 10;

    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();
    private static final GenresService genresService = GenresService.getInstance();

    @Override
    public PaginationInfoHolder<Periodical> getPaginationInfoHolderInstance(HttpServletRequest request) {
        Genre genre = null;
        if (CommandUtils.paramClarifiedInQuery(request, ATTR_GENRE)) {
            genre = genresService.getGenre(request.getParameter(ATTR_GENRE));
        }
        if (genre != null) {
            return getPeriodicalsByGenrePaginationInfoHolder(request, genre);
        }
        return getPeriodicalsPaginationInfoHolder(request);
    }

    private PaginationInfoHolder<Periodical> getPeriodicalsPaginationInfoHolder(HttpServletRequest request) {
        PaginationInfoHolder<Periodical> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = Math.toIntExact(periodicalService.getPeriodicalsCount());
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(RECORDS_PER_PAGE);

        List<Periodical> displayedObjects = periodicalService.getPeriodicalsSublist
                (holder.getSkippedRecordsCount(), holder.getRecordsPerPage());
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(CATALOG_PAGE);

        return holder;
    }

    /**
     * fills PaginationInfoHolder object by sublist of some genre periodicals
     *
     * @see
     */
    private PaginationInfoHolder<Periodical> getPeriodicalsByGenrePaginationInfoHolder(HttpServletRequest request, Genre genre) {
        PaginationInfoHolder<Periodical> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = Math.toIntExact(periodicalService.getGenrePeriodicalsCount(genre));
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(RECORDS_PER_PAGE);

        List<Periodical> displayedObjects = periodicalService.getGenrePeriodicalsSublist
                (genre, holder.getSkippedRecordsCount(), holder.getRecordsPerPage());
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(CATALOG_PAGE + "?genre=" + genre.getId());

        return holder;
    }
}