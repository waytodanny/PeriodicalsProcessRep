package com.periodicals.dao.interfaces;

import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;

import java.util.List;

public interface PaymentsDao extends GenericDao<Payment, String> {
    void addPaymentPeriodicals(Payment payment, List<Periodical> periodicals) throws DaoException;

    void deletePaymentPeriodicals(Payment payment) throws DaoException;

    List<Payment> getUserPaymentsSublist(User user, int take, int limit) throws DaoException;

    int getUserPaymentsCount(User user) throws DaoException;
}
