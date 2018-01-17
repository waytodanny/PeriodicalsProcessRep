package com.periodicals.dao.interfaces;

import com.periodicals.entities.Payment;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;

import java.util.List;

public interface PaymentsDao extends GenericDao<Payment, Long> {
    void addPaymentPeriodicals(Payment payment) throws DaoException;

    void deletePaymentPeriodicals(Long paymentId) throws DaoException;

    List<Payment> getUserPaymentsSublist(User user, int take, int skip) throws DaoException;

    long getUserPaymentsCount(User user) throws DaoException;
}
