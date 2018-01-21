package com.periodicals.services;

import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PeriodicalsJdbcDao;
import com.periodicals.entities.Periodical;
import org.apache.log4j.Logger;

import java.util.List;

import static com.periodicals.utils.ResourceHolders.PagesHolder.CATALOG_PAGE;

public class PaginationService {
    private static final Logger LOGGER = Logger.getLogger(PeriodicalService.class.getSimpleName());

    private static PaginationService paginationService = new PaginationService();

    private static PeriodicalsJdbcDao perDao =
            (PeriodicalsJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalsDao();

    private PaginationService() {

    }

    public static PaginationService getInstance() {
        return paginationService;
    }

    public <T> PaginationInfoHolder<T> getPaginationInfoHolder(int currentPage, int recordsPerPage){
        PaginationInfoHolder<Periodical> holder = new PaginationInfoHolder<>();

        holder.setCurrentPage(currentPage);

        int recordsCount = Math.toIntExact(periodicalService.getPeriodicalsCount());
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(recordsPerPage);

        List<Periodical> displayedObjects = periodicalService.getPeriodicalsSublist
                (holder.getSkippedRecordsCount(), holder.getRecordsPerPage());
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(CATALOG_PAGE);

        return holder;
    }
}
