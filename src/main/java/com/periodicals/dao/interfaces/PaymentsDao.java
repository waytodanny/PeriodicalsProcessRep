package com.periodicals.dao.interfaces;

import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;

import java.util.List;
import java.util.UUID;

public interface PaymentsDao extends GenericDao<Payment, UUID> {
    List<Payment> getPaymentsByUserListBounded(int take, int limit, User user) throws DaoException;

    int getPaymentsByUserCount(User user) throws DaoException;
}

