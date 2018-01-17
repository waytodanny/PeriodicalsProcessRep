package com.periodicals.dao.entities;

import com.periodicals.dao.entities.util.Identified;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Payment implements Identified<Long> {
    private Long id;
    private Timestamp paymentTime;
    private BigDecimal paymentSum;
    private User user;
    private List<Integer> periodicalIdList;

    public Payment() {
        periodicalIdList = new ArrayList<>();
    }

    public Payment(User user, BigDecimal paymentSum) throws IllegalArgumentException {
        this();
        setUser(user);
        setPaymentSum(paymentSum);
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(long id) throws IllegalArgumentException {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must be greater than 0");
        }
        this.id = id;
    }

    public Timestamp getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Timestamp paymentTime) throws IllegalArgumentException {
        if (Objects.isNull(paymentTime)) {
            throw new IllegalArgumentException("passed nullable payment time");
        }
        this.paymentTime = paymentTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(BigDecimal paymentSum) throws IllegalArgumentException {
        if (Objects.isNull(paymentSum) || paymentSum.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("passed nullable or negative payment sum");
        }
        this.paymentSum = paymentSum;
    }

    public List<Integer> getPeriodicalIdList() {
        return periodicalIdList;
    }

    public void setPeriodicalIdList(List<Integer> periodicalIdList) {
        this.periodicalIdList = periodicalIdList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) &&
                Objects.equals(paymentTime, payment.paymentTime) &&
                Objects.equals(paymentSum, payment.paymentSum) &&
                Objects.equals(user, payment.user) &&
                Objects.equals(periodicalIdList, payment.periodicalIdList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, paymentTime, paymentSum, user, periodicalIdList);
    }
}
