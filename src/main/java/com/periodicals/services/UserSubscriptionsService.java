package com.periodicals.services;

import com.periodicals.dao.entities.User;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PaymentsJdbcDao;
import com.periodicals.dao.jdbc.PeriodicalsJdbcDao;
import com.periodicals.dto.PeriodicalDto;
import com.periodicals.dao.entities.Payment;
import com.periodicals.dao.entities.Periodical;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.exceptions.TransactionException;
import com.periodicals.dao.transactions.Transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            Payment payment = new Payment(user, paymentSum);
            Transaction.doTransaction(() -> {
//                payDao.addWithKeyReturn(payment);
                payDao.addPaymentPeriodicals(payment, subs);
                perDao.addUserSubscriptions(user, subs);
            });
        } catch (TransactionException e) {
            throw new ServiceException("failed to process subscriptions: " + e.getMessage());
        }
    }

    public List<PeriodicalDto> getUserSubscriptionsDtoSublist(User user, int skip, int take) {
        List<PeriodicalDto> dtos = new ArrayList<>();
        try {
            List<Periodical> entities = perDao.getUserSubscriptions(user);
            PeriodicalService.fillPeriodicalsDto(entities, dtos);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return dtos;
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
