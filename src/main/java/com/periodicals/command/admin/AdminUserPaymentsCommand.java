package com.periodicals.command.admin;

import com.periodicals.command.util.CommandUtils;
import com.periodicals.command.util.PagedCommand;
import com.periodicals.command.util.PaginationInfoHolder;
import com.periodicals.entities.Payment;
import com.periodicals.services.entities.PaymentService;
import com.periodicals.services.interfaces.PageableCollectionService;
import com.periodicals.utils.uuid.UUIDHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_USER_PAYMENTS_PAGE;

/**
 * @author Daniel Volnitsky
 *
 * Admin command that is responsible for displaying user payments limited lists
 *
 * @see com.periodicals.entities.PeriodicalIssue
 */
public class AdminUserPaymentsCommand extends PagedCommand<Payment> {
    private static final PaymentService paymentService = PaymentService.getInstance();

    @Override
    public PaginationInfoHolder<Payment> getPaginationInfoHolderInstance(HttpServletRequest request) {
        if (CommandUtils.paramClarifiedInQuery(request, "user")) {
            String userIdParam = request.getParameter("user");
            if (UUIDHelper.checkIsUUID(userIdParam)) {
                UUID userId = UUID.fromString(request.getParameter("user"));
                return getUserPaymentsPaginationInfoHolder(request, userId);
            }
        }
        return null;
    }

    @Override
    protected PageableCollectionService<Payment> getPageableCollectionService() {
        return paymentService;
    }

    @Override
    protected String getPageHrefTemplate() {
        return ADMIN_USER_PAYMENTS_PAGE;
    }

    private PaginationInfoHolder<Payment> getUserPaymentsPaginationInfoHolder(HttpServletRequest request, UUID userId) {
        PaginationInfoHolder<Payment> holder = new PaginationInfoHolder<>();

        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
        holder.setCurrentPage(currentPage);

        int recordsCount = paymentService.getPaymentsByUserCount(userId);
        holder.setRecordsCount(recordsCount);
        holder.setRecordsPerPage(this.getRecordsPerPage());

        List<Payment> displayedObjects = paymentService.getPaymentsByUserListBounded
                (holder.getSkippedRecordsCount(), holder.getRecordsPerPage(), userId);
        holder.setDisplayedObjects(displayedObjects);

        holder.setPageHrefTemplate(ADMIN_USER_PAYMENTS_PAGE + "?user_id=" + userId);

        return holder;
    }
}
