package com.periodicals.services;

import com.periodicals.entities.User;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PaymentsJdbcDao;
import com.periodicals.dao.jdbc.PeriodicalsJdbcDao;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.exceptions.TransactionException;
import com.periodicals.dao.transactions.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserSubscriptionsService {
    private static UserSubscriptionsService userSubsService = new UserSubscriptionsService();
    private static PeriodicalsJdbcDao perDao =
            (PeriodicalsJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalsDao();
    private static PaymentsJdbcDao payDao =
            (PaymentsJdbcDao) JdbcDaoFactory.getInstance().getPaymentsDao();

    private UserSubscriptionsService() {

    }

    public static UserSubscriptionsService getInstance() {
        return userSubsService;
    }

    public void processSubscriptions(User user, List<Periodical> subs, BigDecimal paymentSum) throws ServiceException {
        try {
            Payment payment = new Payment();
            payment.setUserId(user.getId());
            payment.setPaymentSum(paymentSum);
            payment.setPeriodicals(subs);

            Transaction.doTransaction(() -> {
                payment.setId(payDao.add(payment));
                payDao.addPaymentPeriodicals(payment);
                perDao.addUserSubscriptions(user, subs);
            });
        } catch (TransactionException e) {
            throw new ServiceException("failed to process subscriptions: " + e.getMessage());
        }
    }

    public List<Periodical> getUserSubscriptionsSublist(User user, int skip, int take) {
        List<Periodical> periodicals = new ArrayList<>();
        try {
            periodicals = perDao.getUserSubscriptions(user);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return periodicals;
    }

    public long getUserSubscriptionsCount(User user) {
        long result = 0;
        try {
            result = perDao.getUserSubscriptionsCount(user);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return result;
    }

    public boolean isSubscribed(User user, Periodical per) {
        boolean subscribed = false;
        try {
            subscribed = perDao.isUserSubscribed(user, per);
        } catch (DaoException e) {
           /*TODO log*/
        }
        return subscribed;
    }

    public List<Periodical> getUserSubscriptions(User user) {
        List<Periodical> subs = new ArrayList<>();
        try {
            subs = perDao.getUserSubscriptions(user);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return subs;
    }

    public void siftAlreadySubscribed(List<Periodical> upToSubs, List<Periodical> userSubs) {
        upToSubs.removeAll(userSubs);
    }
}
