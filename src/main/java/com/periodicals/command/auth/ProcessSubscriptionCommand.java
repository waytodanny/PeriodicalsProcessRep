package com.periodicals.command.auth;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.services.UserSubscriptionsService;
import com.periodicals.utils.Cart;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.periodicals.authentification.AuthenticationHelper.isUserLoggedIn;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.ResourceHolders.PagesHolder.*;

public class ProcessSubscriptionCommand implements Command {
    public static final Logger LOGGER = Logger.getLogger(ProcessSubscriptionCommand.class.getSimpleName());
    private UserSubscriptionsService subsService = UserSubscriptionsService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        if (!isUserLoggedIn(session)) {
            return new CommandResult(req, resp, REDIRECT, LOGIN_PAGE);
        }

        String referer = CommandHelper.getRefererWithoutServletPath(req);

        Cart cart = (Cart) session.getAttribute("cart");
        if (Objects.nonNull(cart)) {
            List<Periodical> upToSubs = cart.getPeriodicals();
            BigDecimal paySum = cart.getQuantity();
            if (Objects.nonNull(upToSubs) && Objects.nonNull(paySum)) {
                User user = (User) session.getAttribute("user");
                try {
                    List<Periodical> userSubs = subsService.getUserSubscriptions(user);
                    subsService.siftAlreadySubscribed(upToSubs, userSubs);
                    subsService.processSubscriptions(user, upToSubs, paySum);
                    return new CommandResult(req, resp, REDIRECT, USER_SUBSCRIPTIONS_PAGE);
                } catch (ServiceException e) {
                    LOGGER.error(e.getMessage());
                    return new CommandResult(req, resp, REDIRECT, referer);
                } finally {
                    cart.cleanUp();
                }
            } else {
                LOGGER.error("payments or payment sum were nullable");
                return new CommandResult(req, resp, REDIRECT, referer);
            }
        } else {
            LOGGER.error("user cart is nullable");
            return new CommandResult(req, resp, REDIRECT, referer);
        }
    }
}
