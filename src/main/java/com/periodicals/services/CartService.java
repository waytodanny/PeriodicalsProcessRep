package com.periodicals.services;

import com.periodicals.entities.Periodical;
import com.periodicals.services.entity.PeriodicalService;
import com.periodicals.utils.Cart;
import org.apache.log4j.Logger;

import java.util.Objects;
import java.util.UUID;

public class CartService {
    private static final Logger LOGGER = Logger.getLogger(CartService.class.getSimpleName());
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();
    private static CartService cartService = new CartService();

    private CartService() {

    }

    public static CartService getInstance() {
        return cartService;
    }

    public void addItemToCart(Cart cart, UUID periodicalId) {
        try {
            Periodical added = periodicalService.getEntityByPrimaryKey(periodicalId);
            if (Objects.nonNull(added)) {
                cart.addItem(added);
                LOGGER.debug("successful adding to cart periodical with id " + periodicalId);
            } else {
                throw new NullPointerException("No periodical founded by id " + periodicalId);
            }
        } catch (Exception e) {
            LOGGER.debug("failed to add to cart periodical with id " + periodicalId);
        }
    }

    public void removeItemFromCart(Cart cart, UUID periodicalId) {
        try {
            Periodical removed = periodicalService.getEntityByPrimaryKey(periodicalId);
            if (Objects.nonNull(removed)) {
                cart.removeItem(removed);
            } else {
                throw new NullPointerException("No periodical founded in cart by id " + periodicalId);
            }
        } catch (Exception e) {
            LOGGER.debug("Failed to delete item from cart: " + e.getMessage());
        }
    }
}
