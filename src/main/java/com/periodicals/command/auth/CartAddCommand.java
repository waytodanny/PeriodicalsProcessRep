package com.periodicals.command.auth;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.Periodical;
import com.periodicals.services.PeriodicalService;
import com.periodicals.utils.Cart;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.periodicals.authentification.AuthenticationHelper.isUserLoggedIn;
import static com.periodicals.command.util.CommandHelper.requiredFieldsNotEmpty;
import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.ResourceHolders.PagesHolder.LOGIN_PAGE;

public class CartAddCommand implements Command {
    private static final Logger LOGGER = Logger.getLogger(CartAddCommand.class.getSimpleName());
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String referer = CommandHelper.getRefererWithoutServletPath(req);

        if (isUserLoggedIn(req.getSession())) {
            String addId = req.getParameter("perId");
            if (requiredFieldsNotEmpty(addId)) {
                HttpSession session = req.getSession();
                Cart cart = (Cart) session.getAttribute("cart");
                try {
                    int addIdValue = Integer.parseInt(addId);
                    Periodical added = periodicalService.getPeriodicalById(addIdValue);
                    if (Objects.isNull(cart)) {
                        cart = new Cart();
                        cart.addItem(added);
                        session.setAttribute("cart", cart);
                    } else{
                        cart.addItem(added);
                    }
                    LOGGER.debug("successful adding to cart");
                } catch (Exception e) {
                    LOGGER.error("Failed to add periodical to cart: " + e.getMessage());
                }
            }
            return new CommandResult(req, resp, REDIRECT, referer);
        } else {
            return new CommandResult(req, resp, FORWARD, LOGIN_PAGE);
        }
    }
}
