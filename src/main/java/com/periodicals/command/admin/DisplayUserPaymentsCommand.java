//package com.periodicals.command.admin;
//
//import com.periodicals.command.util.PagedCommand;
//import com.periodicals.command.util.PaginationInfoHolder;
//import com.periodicals.entities.Payment;
//import com.periodicals.entities.User;
//import com.periodicals.services.PaymentService;
//import com.periodicals.services.UserService;
//import com.periodicals.services.util.PageableCollectionService;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//import java.util.Objects;
//
//import static com.periodicals.utils.resourceHolders.PagesHolder.ADMIN_USER_PAYMENTS_PAGE;
//
//public class DisplayUserPaymentsCommand extends PagedCommand<Payment> {
//    private static final int RECORDS_PER_PAGE = 10;
//
//    private static final UserService userService = UserService.getInstance();
//    private static final PaymentService paymentService = PaymentService.getInstance();
//
//
//    @Override
//    public PaginationInfoHolder<Payment> getPaginationInfoHolderInstance(HttpServletRequest request) {
//        User user = userService.getUserById(request.getParameter("user_id"));
//        if(Objects.nonNull(user)) {
//            return getUserPaymentsPaginationInfoHolder(request, user);
//        }
//        return null;
//    }
//
//    @Override
//    protected PageableCollectionService<Payment> getPageableCollectionService() {
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
//    private PaginationInfoHolder<Payment> getUserPaymentsPaginationInfoHolder(HttpServletRequest request, User user) {
//        PaginationInfoHolder<Payment> holder = new PaginationInfoHolder<>();
//
//        int currentPage = PaginationInfoHolder.getPageFromRequest(request);
//        holder.setCurrentPage(currentPage);
//
//        int recordsCount = Math.toIntExact(paymentService.getUserPaymentsCount(user));
//        holder.setRecordsCount(recordsCount);
//        holder.setRecordsPerPage(RECORDS_PER_PAGE);
//
//        List<Payment> displayedObjects = paymentService.getUserPaymentsLimited
//                (user, holder.getSkippedRecordsCount(), holder.getRecordsPerPage());
//        holder.setDisplayedObjects(displayedObjects);
//
//        holder.setPageHrefTemplate(ADMIN_USER_PAYMENTS_PAGE + "?user_id=" + user.getId());
//
//        return holder;
//    }
//}
