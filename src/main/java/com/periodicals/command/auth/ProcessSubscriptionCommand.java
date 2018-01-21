package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.services.UserSubscriptionsService;
import com.periodicals.utils.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.ResourceHolders.PagesHolder.LOGIN_PAGE;

public class ProcessSubscriptionCommand implements Command {
    private UserSubscriptionsService userSubscriptionsService = UserSubscriptionsService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        if (!AuthenticationHelper.isUserLoggedIn(session)) {
            return new CommandResult(REDIRECT, LOGIN_PAGE);
        }

        Cart cart = this.getCartFromSession(session);
        if (Objects.nonNull(cart)) {
            List<Periodical> cartItems = cart.getPeriodicals();
            BigDecimal paySum = cart.getQuantity();
            if (Objects.nonNull(cartItems) && Objects.nonNull(paySum)) {
                try {
                    User user = AuthenticationHelper.getUserFromSession(session);
                    userSubscriptionsService.processSubscriptions(user, cartItems, paySum);
                    /*
                    List<Periodical> userSubs = userSubscriptionsService.getUserSubscriptions(user);
                    userSubscriptionsService.siftAlreadySubscribed(upToSubs, userSubs);
                    */
                    request.setAttribute("resultMessage", "Successfully processed subscriptions");
                } catch (ServiceException e) {
                    request.setAttribute("resultMessage", "Failed to process subscriptions");
                } finally {
                    cart.cleanUp();
                }
            }
        }

        return null;
    }

    private Cart getCartFromSession(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        return cart;
    }
}
