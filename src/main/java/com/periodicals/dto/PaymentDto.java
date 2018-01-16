package com.periodicals.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class PaymentDto {
    private Long id;
    private Timestamp paymentTime;
    private String userId;
    private BigDecimal paymentSum;
    private List<PeriodicalDto> periodicals;

    public PaymentDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Timestamp paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getPaymentSum() {
        return paymentSum;
    }

    public void setPaymentSum(BigDecimal paymentSum) {
        this.paymentSum = paymentSum;
    }

    public List<PeriodicalDto> getPeriodicals() {
        return periodicals;
    }

    public void setPeriodicals(List<PeriodicalDto> periodicals) {
        this.periodicals = periodicals;
    }
}
