package com.periodicals.command.util;

import com.periodicals.services.util.PageableCollectionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Objects;

import static com.periodicals.command.util.RedirectType.*;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ERROR_PAGE;

public abstract class PagedCommand<T> implements Command {

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        PaginationInfoHolder<T> paginationInfoHolder = this.getPaginationInfoHolderInstance(request);

        if(Objects.nonNull(paginationInfoHolder)) {
            paginationInfoHolder.setAttributesToRequest(request);
        } else {
            return new CommandResult(FORWARD, ERROR_PAGE);
        }

        return new CommandResult(FORWARD, paginationInfoHolder.getCurrentPageHref());
    }
    protected PaginationInfoHolder<T> getPaginationInfoHolderInstance(HttpServletRequest request) {
        return getBaseEntityPaginationHolder(request);
    }

    private PaginationInfoHolder<T> getBaseEntityPaginationHolder(HttpServletRequest request) {
        PageableCollectionService<T> service = this.getPageableCollectionService();
        PaginationInfoHolder<T> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = Math.toIntExact(service.getEntitiesCount());
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(this.getRecordsPerPage());

        List<T> displayedObjects = service.getEntitiesListBounded
                (holder.getSkippedRecordsCount(), holder.getRecordsPerPage());
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(this.getPageHrefTemplate());

        return holder;
    }

    protected abstract PageableCollectionService<T> getPageableCollectionService();

    protected abstract int getRecordsPerPage();

    protected abstract String getPageHrefTemplate();
}
