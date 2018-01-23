package com.periodicals.command.auth;

import com.periodicals.command.util.CommandUtils;
import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.Periodical;
import com.periodicals.services.entity.PeriodicalService;
import com.periodicals.services.util.PageableCollectionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.AttributesHolder.ATTR_GENRE;
import static com.periodicals.utils.resourceHolders.PagesHolder.CATALOG_PAGE;

public class CatalogCommand extends PagedCommand<Periodical> {
    private static final int RECORDS_PER_PAGE = 10;

    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    @Override
    public PaginationInfoHolder<Periodical> getPaginationInfoHolderInstance(HttpServletRequest request) {
        try {
            if (CommandUtils.paramClarifiedInQuery(request, ATTR_GENRE)) {
                UUID genreId = UUID.fromString(request.getParameter(ATTR_GENRE));
                return getPeriodicalsByGenrePaginationInfoHolder(request, genreId);
            }
            return super.getPaginationInfoHolderInstance(request);

        } catch (IllegalArgumentException e) {
            return super.getPaginationInfoHolderInstance(request);
        }
    }

    @Override
    protected PageableCollectionService<Periodical> getPageableCollectionService() {
        return periodicalService;
    }

    /*TODO  no query params?*/
    @Override
    protected String getPageHrefTemplate() {
        return CATALOG_PAGE;
    }

    /**
     * fills PaginationInfoHolder object by sublist of some genre periodicals
     *
     * @see
     */
    private PaginationInfoHolder<Periodical> getPeriodicalsByGenrePaginationInfoHolder(HttpServletRequest request, UUID genreId) {
        PaginationInfoHolder<Periodical> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = periodicalService.getPeriodicalsByGenreCount(genreId);
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(RECORDS_PER_PAGE);

        List<Periodical> displayedObjects = periodicalService.getPeriodicalsByGenreListBounded(
                genreId, holder.getSkippedRecordsCount(), holder.getRecordsPerPage());
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(CATALOG_PAGE + "?genre=" + genreId);

        return holder;
    }
}