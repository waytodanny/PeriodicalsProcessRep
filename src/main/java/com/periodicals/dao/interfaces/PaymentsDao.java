package com.periodicals.dao.interfaces;

import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;

import java.util.List;
import java.util.UUID;

public interface PaymentsDao extends GenericDao<Payment, UUID> {
    void addPaymentPeriodicals(Payment payment, List<Periodical> periodicals) throws DaoException;

    void deletePaymentPeriodicals(Payment payment) throws DaoException;

    List<Payment> getUserPaymentsListBounded(User user, int take, int limit) throws DaoException;

    int getUserPaymentsCount(User user) throws DaoException;

    List<Payment> getPaymentsListBounded(int skip, int limit) throws DaoException;
}
