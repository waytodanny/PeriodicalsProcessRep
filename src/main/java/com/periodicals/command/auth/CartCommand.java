package com.periodicals.command.auth;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.Periodical;
import com.periodicals.services.PeriodicalService;
import com.periodicals.utils.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static com.periodicals.authentification.AuthenticationHelper.isUserLoggedIn;
import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.command.util.RedirectType.REDIRECT;
import static com.periodicals.utils.AttributesHolder.SERVLET_ROOT;
import static com.periodicals.utils.PagesHolder.LOGIN_PAGE;

public class CartCommand implements Command {

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {

        if (!isUserLoggedIn(req.getSession())) {
            return new CommandResult(req, resp, REDIRECT, LOGIN_PAGE);
        }

        String referer = CommandHelper.getRefererWithoutServletPath(req);

        String remId = req.getParameter("remId");
        if (Objects.nonNull(remId)) {
            return removeFromCartResult(req, resp, remId, referer);
        }
        String addId = req.getParameter("perId");
        if (Objects.nonNull(addId)) {
            return addToCartResult(req, resp, addId, referer);
        }
        return new CommandResult(req, resp, REDIRECT, referer);
    }

    private CommandResult addToCartResult(HttpServletRequest req, HttpServletResponse resp, String reqId, String referer) {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (Objects.isNull(cart)) {
            cart = new Cart();
        }
        try {
            int perId = Integer.parseInt(reqId);
            Periodical added = PeriodicalService.getInstance().getPeriodicalById(perId);

            cart.addItem(added);
            session.setAttribute("cart", cart);
        } catch (Exception e) {
            /*TODO log*/
        }

        return new CommandResult(req, resp, REDIRECT, referer);
    }

    private CommandResult removeFromCartResult(HttpServletRequest req, HttpServletResponse resp, String remId, String referer) {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        Periodical removable = cart.getPeriodicalById(Integer.parseInt(remId));
        cart.removeItem(removable);
        session.setAttribute("cart", cart);
        return new CommandResult(req, resp, REDIRECT, referer);
    }
}

