package com.periodicals.command.auth;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.services.CartService;
import com.periodicals.utils.entities.Cart;
import com.periodicals.utils.uuid.UUIDHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.AttributesHolder.ATTR_CART;

/**
 * @author Daniel Volnitsky
 *
 * Command for authenticated users that is responsible for handling
 * user adding to cart action
 * @see Cart
 */
public class CartAddItemCommand implements Command {
    private static final CartService cartService = CartService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (AuthenticationHelper.isUserLoggedIn(session)) {
            if (CommandUtils.paramClarifiedInQuery(request, "item_id")) {
                String itemId = request.getParameter("item_id");
                if (UUIDHelper.isUUID(itemId)) {
                    Cart cart = this.getCartFromSession(session);
                    if (Objects.nonNull(cart)) {
                        cartService.addItemToCart(cart, UUID.fromString(itemId));
                    }
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
