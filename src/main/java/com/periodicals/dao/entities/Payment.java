package com.periodicals.dao.entities;

import com.periodicals.dao.entities.util.Identified;
import com.periodicals.dto.PeriodicalDto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Payment implements Identified<Long> {
    private Long id;
    private Timestamp paymentTime;
    private String userId;
    private BigDecimal paymentSum;
    private List<Periodical> periodicals;


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

    public String getUserId() {
        return userId;
    }

    /*TODO decide whether is right to check here*/
    public void setUserId(String userId) {
        this.userId = userId;
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

    public List<Periodical> getPeriodicals() {
        return periodicals;
    }

    public void setPeriodicals(List<Periodical> periodicals) {
        this.periodicals = periodicals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) &&
                Objects.equals(paymentTime, payment.paymentTime) &&
                Objects.equals(userId, payment.userId) &&
                Objects.equals(paymentSum, payment.paymentSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentTime, userId, paymentSum);
    }
}
