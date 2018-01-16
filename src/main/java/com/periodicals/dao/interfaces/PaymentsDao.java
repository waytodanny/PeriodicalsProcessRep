package com.periodicals.dao.interfaces;

import com.periodicals.dao.entities.Payment;
import com.periodicals.dao.entities.Periodical;
import com.periodicals.dao.entities.User;
import com.periodicals.exceptions.DaoException;

import java.util.List;

public interface PaymentsDao extends GenericDao<Payment, Long> {
    boolean addPaymentPeriodicals(Payment payment, List<Periodical> subs) throws DaoException;

    List<Integer> getPaymentPeriodicals(Long paymentId) throws DaoException;

    void deletePaymentPeriodicals(Long paymentId) throws DaoException;

    List<Payment> getUserPaymentsSublist(User user, int take, int skip);

    int getUserPaymentsCount(User user);
}
