package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.services.CartService;
import com.periodicals.utils.entities.Cart;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

public class CartRemoveItemCommand implements Command {
    private static final CartService cartService = CartService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (AuthenticationHelper.isUserLoggedIn(session)) {
            if (!CommandUtils.paramClarifiedInQuery(request, "item_id")) {
                UUID removedId = UUID.fromString(request.getParameter("item_id"));
                cartService.removeItemFromCart(getCartFromSession(session), removedId);
            }
        }
        return null;
    }

    private Cart getCartFromSession(HttpSession session) {
        return (Cart) session.getAttribute("cart");
    }
}
