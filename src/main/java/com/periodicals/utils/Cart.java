package com.periodicals.utils;

import com.periodicals.dao.entities.Periodical;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Cart {
    private List<Periodical> periodicals;
    private BigDecimal quantity;

    public Cart() {
        periodicals = new LinkedList<>();
        quantity = new BigDecimal(0)
                .setScale(2, BigDecimal.ROUND_DOWN);
    }

    public void addItem(Periodical periodical) {
        if (!isInCart(periodical)) {
            periodicals.add(periodical);
            quantity = quantity.add(periodical.getSubscriptionCost())
                    .setScale(2, BigDecimal.ROUND_DOWN);
        }
    }

    public void removeItem(Periodical periodical) {
        if (isInCart(periodical)) {
            periodicals.remove(periodical);
            quantity = quantity.subtract(periodical.getSubscriptionCost())
                    .setScale(2, BigDecimal.ROUND_DOWN);
        }
    }

    public List<Periodical> getPeriodicals() {
        return periodicals;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    private boolean isInCart(Periodical periodical) {
        return periodicals.contains(periodical);
    }

    public Periodical getPeriodicalById(Integer id) {
        for (Periodical per : periodicals) {
            if(per.getId().equals(id))
                return per;
        }
        return null;
    }
}
