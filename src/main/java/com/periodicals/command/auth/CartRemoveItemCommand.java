package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.entities.Periodical;
import com.periodicals.services.CartService;
import com.periodicals.utils.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CartRemoveItemCommand implements Command {
    private static final CartService cartService = CartService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (AuthenticationHelper.isUserLoggedIn(session)) {
            if (!CommandUtils.paramClarifiedInQuery(request,"item_id")) {
                try {
//                    UUID itemId = UUID.fromString(request.getParameter("item_id"));
                    String removedItemId = request.getParameter("item_id");
                    Cart cart = getCartFromSession(session);
                    Periodical removed = cart.getPeriodicalById(removedItemId);
                    cartService.removeItemFromCart(cart, removed);
                } catch (Exception e) {
//                    request.setAttribute("resultMessage", "Failed to remove item from cart");
                }
            }
        }
        return null;
    }

    private Cart getCartFromSession(HttpSession session) {
        return (Cart) session.getAttribute("cart");
    }
}
