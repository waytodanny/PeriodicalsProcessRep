package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.User;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.services.entities.PaymentService;
import com.periodicals.utils.entities.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.resourceHolders.PagesHolder.LOGIN_PAGE;

/**
 * @author Daniel Volnitsky
 * <p>
 * Command for authenticated users that is responsible for providing paymentService with
 * info about incoming payment
 * @see PaymentService
 */
public class ProcessSubscriptionCommand implements Command {
    private PaymentService paymentService = PaymentService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        if (!AuthenticationHelper.isUserLoggedIn(session)) {
            return new CommandResult(REDIRECT, LOGIN_PAGE);
        }

        Cart cart = this.getCartFromSession(session);
        if (Objects.nonNull(cart)) {
            try {
                User user = AuthenticationHelper.getUserFromSession(session);
                if (Objects.nonNull(user)) {
                    paymentService.createEntity(
                            user,
                            cart.getTotalValue(),
                            cart.getItems()
                    );
                    request.setAttribute("resultMessage", "Successfully processed subscriptions");
                }
            } catch (ServiceException e) {
                request.setAttribute("resultMessage", "Failed to process subscriptions");
            } finally {
                cart.cleanUp();
            }
        }
        return null;
    }

    private Cart getCartFromSession(HttpSession session) {
        return (Cart) session.getAttribute("cart");
    }
}
