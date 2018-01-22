package com.periodicals.utils;

import com.periodicals.entities.Periodical;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Cart {
    private List<Periodical> items;
    private BigDecimal totalValue;

    public Cart() {
        items = new LinkedList<>();
        totalValue = new BigDecimal(0)
                .setScale(2, BigDecimal.ROUND_DOWN);
    }

    public void addItem(Periodical periodical) {
        if (!isInCart(periodical)) {
            items.add(periodical);
            totalValue = totalValue.add(periodical.getSubscriptionCost())
                    .setScale(2, BigDecimal.ROUND_DOWN);
        }
    }

    public void removeItem(Periodical periodical) {
        if (isInCart(periodical)) {
            items.remove(periodical);
            totalValue = totalValue.subtract(periodical.getSubscriptionCost())
                    .setScale(2, BigDecimal.ROUND_DOWN);
        }
    }

    public void cleanUp() {
        this.items = new LinkedList<>();
        this.totalValue = new BigDecimal(0);
    }

    public List<Periodical> getItems() {
        return items;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    private boolean isInCart(Periodical periodical) {
        return items.contains(periodical);
    }
}
