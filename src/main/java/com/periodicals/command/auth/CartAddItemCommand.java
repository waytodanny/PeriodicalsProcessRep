package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.entities.Periodical;
import com.periodicals.services.CartService;
import com.periodicals.services.PeriodicalService;
import com.periodicals.utils.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.periodicals.utils.ResourceHolders.AttributesHolder.ATTR_CART;

public class CartAddItemCommand implements Command {
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();
    private static final CartService cartService = CartService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (AuthenticationHelper.isUserLoggedIn(session)) {
            if (!CommandUtils.paramClarifiedInQuery(request,"item_id")) {
                try {
//                    UUID itemId = UUID.fromString(request.getParameter("item_id"));
                    String addedItemId = request.getParameter("item_id");
                    Periodical added = periodicalService.getPeriodicalById(addedItemId);
                    cartService.addItemToCart(this.getCartFromSession(session), added);
                } catch (Exception e) {
//                    request.setAttribute("resultMessage", "Failed to add item to cart");
                }
            }
        }

        return null;
    }

    private Cart getCartFromSession(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(ATTR_CART);
        if (Objects.isNull(cart)) {
            cart = new Cart();
            session.setAttribute(ATTR_CART, cart);
        }
        return cart;
    }
}
