package com.periodicals.command.admin;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.Payment;
import com.periodicals.entities.User;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.services.PaymentService;
import com.periodicals.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_USER_INFO_PAGE;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ERROR_PAGE;

public class AdminUserInfoCommand implements Command {
    private static final int RECORDS_PER_PAGE = 5;

    private UserService userService = UserService.getInstance();
    private PaymentService paymentService = PaymentService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("id");

        if (!CommandHelper.paramIsNotEmpty(userId)) {
            return new CommandResult(req, resp, FORWARD, ERROR_PAGE);
        }

        int page = 1;
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));


            User user = userService.getUserById(userId);

            long entriesCount = paymentService.getUserPaymentsCount(user);
            int pagesCount = (int) Math.ceil(entriesCount * 1.0 / RECORDS_PER_PAGE);

            int skip = (page - 1) * RECORDS_PER_PAGE;
            List<Payment> userPayments = paymentService.getUserPaymentsSublist(user, skip, RECORDS_PER_PAGE);

            req.setAttribute("payments", userPayments);
            req.setAttribute("pagesCount", pagesCount);
            req.setAttribute("currentPage", page);

            return new CommandResult(req, resp, FORWARD, ADMIN_USER_INFO_PAGE + "?id=" + userId + "&page=" + page);

    }
}
