package com.periodicals.command.auth;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.Periodical;
import com.periodicals.utils.Cart;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.periodicals.authentification.AuthenticationHelper.isUserLoggedIn;
import static com.periodicals.command.util.CommandHelper.requiredFieldsNotEmpty;
import static com.periodicals.command.util.RedirectType.REDIRECT;

public class CartRemoveCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CartRemoveCommand.class.getSimpleName());

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String referer = CommandHelper.getRefererWithoutServletPath(req);

        if (isUserLoggedIn(req.getSession())) {
            String remId = req.getParameter("remId");
            if (requiredFieldsNotEmpty(remId)) {
                HttpSession session = req.getSession();
                Cart cart = (Cart) session.getAttribute("cart");
                try {
                    int remIdValue = Integer.parseInt(remId);
                    Periodical removable = cart.getPeriodicalById(remIdValue);
                    cart.removeItem(removable);
                    LOGGER.debug("successful removing from cart");
                } catch (Exception e) {
                    LOGGER.error("Failed to remove periodical from cart: " + e.getMessage());
                }
            }
        }
        return new CommandResult(req, resp, REDIRECT, referer);
    }
}
