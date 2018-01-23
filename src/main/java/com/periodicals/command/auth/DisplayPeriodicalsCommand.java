package com.periodicals.command.auth;

import com.periodicals.command.util.CommandUtils;
import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.Periodical;
import com.periodicals.services.entities.PeriodicalService;
import com.periodicals.services.interfaces.PageableCollectionService;
import com.periodicals.utils.uuid.UUIDHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.AttributesHolder.ATTR_GENRE;
import static com.periodicals.utils.resourceHolders.PagesHolder.CATALOG_PAGE;

// TODO НЕ ВЫВОДИТЬ КНОПКУ ПОКУПКИ ДЛЯ ИЗДАНИЙ, НА КОТОРЫЕ ПОЛЬЗОВАТЕЛЬ УЖЕ ПОДПИСАН

public class DisplayPeriodicalsCommand extends PagedCommand<Periodical> {
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    @Override
    public PaginationInfoHolder<Periodical> getPaginationInfoHolderInstance(HttpServletRequest request) {
        if (CommandUtils.paramClarifiedInQuery(request, ATTR_GENRE)) {
            String genreId = request.getParameter(ATTR_GENRE);
            if (UUIDHelper.isUUID(genreId)) {
                return getPeriodicalsByGenrePaginationInfoHolder(request, UUID.fromString(genreId));
            }
        }
        return super.getPaginationInfoHolderInstance(request);
    }

    @Override
    protected PageableCollectionService<Periodical> getPageableCollectionService() {
        return periodicalService;
    }

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
        holder.setRecordsPerPage(this.getRecordsPerPage());

        List<Periodical> displayedObjects = periodicalService.getPeriodicalsByGenreListBounded(
                holder.getSkippedRecordsCount(), holder.getRecordsPerPage(), genreId);
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(this.getPageHrefTemplate() + "?genre=" + genreId);

        return holder;
    }
}