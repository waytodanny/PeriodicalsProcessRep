package com.periodicals.utils;

import com.periodicals.entities.Periodical;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Cart {
    private List<Periodical> periodicals;
    private BigDecimal totalValue;

    public Cart() {
        periodicals = new LinkedList<>();
        totalValue = new BigDecimal(0)
                .setScale(2, BigDecimal.ROUND_DOWN);
    }

    public void addItem(Periodical periodical) {
        if (!isInCart(periodical)) {
            periodicals.add(periodical);
            totalValue = totalValue.add(periodical.getSubscriptionCost())
                    .setScale(2, BigDecimal.ROUND_DOWN);
        }
    }

    public void removeItem(Periodical periodical) {
        if (isInCart(periodical)) {
            periodicals.remove(periodical);
            totalValue = totalValue.subtract(periodical.getSubscriptionCost())
                    .setScale(2, BigDecimal.ROUND_DOWN);
        }
    }

    public void cleanUp() {
        this.periodicals = new LinkedList<>();
        this.totalValue = new BigDecimal(0);
    }

    public List<Periodical> getPeriodicals() {
        return periodicals;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
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
