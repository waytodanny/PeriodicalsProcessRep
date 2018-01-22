package com.periodicals.services.entity;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PaymentsJdbcDao;
import com.periodicals.dao.jdbc.PeriodicalsJdbcDao;
import com.periodicals.dao.transactions.Transaction;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.exceptions.TransactionException;
import com.periodicals.utils.uuid.UuidGenerator;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubscriptionsService {
    private static final Logger LOGGER = Logger.getLogger(SubscriptionsService.class.getSimpleName());

    private static SubscriptionsService userSubsService = new SubscriptionsService();
    private static PeriodicalsJdbcDao perDao =
            (PeriodicalsJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalsDao();
    private static PaymentsJdbcDao payDao =
            (PaymentsJdbcDao) JdbcDaoFactory.getInstance().getPaymentsDao();

    private SubscriptionsService() {

    }

    public static SubscriptionsService getInstance() {
        return userSubsService;
    }

    public void processSubscriptions(User user, List<Periodical> newSubscriptions, BigDecimal paymentSum) throws ServiceException {

        /*TODO check it earlier in catalog*/
        List<Periodical> userSubscriptions = getUserSubscriptions(user);
        siftAlreadySubscribed(newSubscriptions, userSubscriptions);
        /*TODO*/

        try {
            Payment payment = new Payment();
            payment.setId(UuidGenerator.generateUuid());
            payment.setUserId(user.getId());
            payment.setPaymentSum(paymentSum);
            payment.setPeriodicals(newSubscriptions);

            Transaction.doTransaction(() -> {
                payDao.createEntity(payment);
                payDao.addPaymentPeriodicals(payment, newSubscriptions);
                perDao.addUserSubscriptions(user, newSubscriptions);
            });

        } catch (TransactionException e) {
            throw new ServiceException("failed to process subscriptions: " + e.getMessage());
        }
    }

    public List<Periodical> getUserSubscriptionsLimited(User user, int skip, int take) {
        List<Periodical> periodicals = new ArrayList<>();
        try {
            periodicals = perDao.getUserSubscriptionsLimited(user, skip, take);
            LOGGER.debug("Succeed to get user subscriptions limited list");
        } catch (DaoException e) {
            LOGGER.debug("Failed to get user subscriptions limited list: " + e.getMessage());
        }
        return periodicals;
    }

    public long getUserSubscriptionsCount(User user) {
        long result = 0;
        try {
            result = perDao.getUserSubscriptionsCount(user);
            LOGGER.debug("Succeed to get user subscriptions count");
        } catch (DaoException e) {
            LOGGER.debug("Failed to get user subscriptions count: " + e.getMessage());
        }
        return result;
    }

    public boolean isSubscribed(User user, UUID periodicalId) {
        boolean subscribed = false;
        try {
            Periodical periodical = perDao.getEntityByPrimaryKey(periodicalId);
            subscribed = perDao.isUserSubscribed(user, periodical);
            LOGGER.debug("Succeed to get whether user with id " + user.getId() +
                    " subscribed on periodical with id " + periodicalId);
        } catch (DaoException e) {
            LOGGER.debug("Failed to get whether user with id " + user.getId() +
                    " subscribed on periodical with id " + periodicalId);
        }
        return subscribed;
    }

    public List<Periodical> getUserSubscriptions(User user) {
        List<Periodical> subs = new ArrayList<>();
        try {
            subs = perDao.getUserSubscriptions(user);
            LOGGER.debug("Succeed to get user with id " + user.getId() + " subscriptions");
        } catch (DaoException e) {
            LOGGER.debug("Failed to get user with id " + user.getId() + " subscriptions");
        }
        return subs;
    }

    private void siftAlreadySubscribed(List<Periodical> upToSubs, List<Periodical> userSubs) {
        upToSubs.removeAll(userSubs);
    }
}
