package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.GenresJdbcDao;
import com.periodicals.entities.Periodical;
import com.periodicals.utils.Cart;

public class CartService {
    private static CartService cartService = new CartService();

    private CartService() {

    }

    public static CartService getInstance() {
        return cartService;
    }

    public void addItemToCart(Cart cart, Periodical periodical) {
        cart.addItem(periodical);
    }

    public void removeItemFromCart(Cart cart, Periodical periodical) {
        cart.removeItem(periodical);
    }
}
