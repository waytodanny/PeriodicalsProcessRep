package com.periodicals.command.admin;

import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.User;
import com.periodicals.services.entity.UserService;
import com.periodicals.services.util.PageableCollectionService;

import javax.servlet.http.HttpServletRequest;

import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_USERS_PAGE;

public class DisplayUsersCommand extends PagedCommand<User> {
    private static final int RECORDS_PER_PAGE = 10;

    private static final UserService userService = UserService.getInstance();

    @Override
    protected PaginationInfoHolder<User> getPaginationInfoHolderInstance(HttpServletRequest request) {
        return super.getPaginationInfoHolderInstance(request);
    }

    @Override
    protected PageableCollectionService<User> getPageableCollectionService() {
        return userService;
    }

    @Override
    protected int getRecordsPerPage() {
        return RECORDS_PER_PAGE;
    }

    @Override
    protected String getPageHrefTemplate() {
        return ADMIN_USERS_PAGE;
    }

//    private PaginationInfoHolder<User> getUsersPaginationInfoHolder(HttpServletRequest request) {
//        PaginationInfoHolder<User> holder = new PaginationInfoHolder<>();
//
//        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
//        holder.setCurrentPage(currentPage);
//
//        int recordsCount = Math.toIntExact(userService.getUsersCount());
//        holder.setRecordsCount(recordsCount);
//        holder.setRecordsPerPage(RECORDS_PER_PAGE);
//
//        List<User> displayedObjects = userService.getUsersSublist
//                (holder.getSkippedRecordsCount(), holder.getRecordsPerPage());
//        holder.setDisplayedObjects(displayedObjects);
//
//        holder.setPageHrefTemplate(ADMIN_USERS_PAGE);
//
//        return holder;
//    }
}
