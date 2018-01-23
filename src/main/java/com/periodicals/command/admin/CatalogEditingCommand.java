//package com.periodicals.command.admin;
//
//import com.periodicals.command.util.PagedCommand;
//import com.periodicals.command.util.PaginationInfoHolder;
//import com.periodicals.entities.Periodical;
//import com.periodicals.services.PeriodicalService;
//import com.periodicals.services.util.PageableCollectionService;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
//import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_PERIODICALS_EDIT_PAGE;
//
//public class CatalogEditingCommand extends PagedCommand<Periodical> {
//    private static final int RECORDS_PER_PAGE = 10;
//
//    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();
//
//    @Override
//    protected PaginationInfoHolder<Periodical> getPaginationInfoHolderInstance(HttpServletRequest request) {
//        return getPeriodicalsPaginationInfoHolder(request);
//    }
//
//    @Override
//    protected PageableCollectionService<Periodical> getPageableCollectionService() {
//        return null;
//    }
//
//    @Override
//    protected int getRecordsPerPage() {
//        return 0;
//    }
//
//    @Override
//    protected String getPageHrefTemplate() {
//        return null;
//    }
//
//    private PaginationInfoHolder<Periodical> getPeriodicalsPaginationInfoHolder(HttpServletRequest request) {
//        PaginationInfoHolder<Periodical> holder = new PaginationInfoHolder<>();
//
//        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
//        holder.setCurrentPage(currentPage);
//
//        int recordsCount = Math.toIntExact(periodicalService.getPeriodicalsCount());
//        holder.setRecordsCount(recordsCount);
//        holder.setRecordsPerPage(RECORDS_PER_PAGE);
//
//        List<Periodical> displayedObjects = periodicalService.getPeriodicalsSublist
//                (holder.getSkippedRecordsCount(), holder.getRecordsPerPage());
//        holder.setDisplayedObjects(displayedObjects);
//
//        holder.setPageHrefTemplate(ADMIN_PERIODICALS_EDIT_PAGE);
//
//        return holder;
//    }
//}
