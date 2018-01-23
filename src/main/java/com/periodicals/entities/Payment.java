package com.periodicals.entities;

import com.periodicals.entities.util.Identified;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 *
 * Class that represents payment made by some user purchasing some periodicals
 * @see User
 * @see Periodical
 */
public class Payment implements Identified<UUID> {
    private UUID id;
    private Timestamp paymentTime;
    private UUID userId;
    private BigDecimal paymentSum;
    private List<Periodical> periodicals;

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) throws IllegalArgumentException {
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

    public UUID getUserId() {
        return userId;
    }

    /*TODO decide whether is right to check here*/
    public void setUserId(UUID userId) {
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
                Objects.equals(paymentSum, payment.paymentSum) &&
                Objects.equals(periodicals, payment.periodicals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, paymentTime, userId, paymentSum, periodicals);
    }
}
