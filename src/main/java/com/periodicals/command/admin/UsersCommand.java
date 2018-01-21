package com.periodicals.command.admin;

import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.User;
import com.periodicals.services.UserService;
import com.periodicals.services.util.PageableCollectionService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_USERS_PAGE;

public class UsersCommand extends PagedCommand<User> {
    private static final int RECORDS_PER_PAGE = 10;

    private static final UserService userService = UserService.getInstance();

    @Override
    protected PaginationInfoHolder<User> getPaginationInfoHolderInstance(HttpServletRequest request) {
        return getUsersPaginationInfoHolder(request);
    }

    @Override
    protected PageableCollectionService<User> getPageableCollectionService() {
        return null;
    }

    @Override
    protected int getRecordsPerPage() {
        return 0;
    }

    @Override
    protected String getPageHrefTemplate() {
        return null;
    }

    private PaginationInfoHolder<User> getUsersPaginationInfoHolder(HttpServletRequest request) {
        PaginationInfoHolder<User> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = Math.toIntExact(userService.getUsersCount());
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(RECORDS_PER_PAGE);

        List<User> displayedObjects = userService.getUsersSublist
                (holder.getSkippedRecordsCount(), holder.getRecordsPerPage());
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(ADMIN_USERS_PAGE);

        return holder;
    }
}
