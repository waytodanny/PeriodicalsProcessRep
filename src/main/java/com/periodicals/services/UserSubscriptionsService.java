package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PaymentsJdbcDao;
import com.periodicals.dao.jdbc.PeriodicalsJdbcDao;
import com.periodicals.dto.PeriodicalDto;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.exceptions.TransactionException;
import com.periodicals.transactions.Transaction;

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

    public void processSubscriptions(String uuid, List<Periodical> subs, BigDecimal paymentSum) throws ServiceException {
        try {
            Payment payment = new Payment(uuid, paymentSum);
            Transaction.doTransaction(() -> {
                payDao.addWithKeyReturn(payment);
                payDao.addPaymentPeriodicals(payment, subs);
                perDao.addUserSubscriptions(uuid, subs);
            });
        } catch (TransactionException e) {
            throw new ServiceException("failed to process subscriptions: " + e.getMessage());
        }
    }

    public List<PeriodicalDto> getUserSubscriptionsDtoSublist(String uuid, int skip, int take) {
        List<PeriodicalDto> dtos = new ArrayList<>();
        try {
            List<Periodical> entities = perDao.getUserSubscriptions(uuid);
            PeriodicalService.fillPeriodicalsDto(entities, dtos);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return dtos;
    }

    public int getUserSubscriptionsCount(String uuid) {
        int result = 0;
        try {
            result = perDao.getUserSubscriptionsCount(uuid);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return result;
    }

    public boolean isSubscribed(String uuid, Periodical per) {
        boolean subscribed = false;
        try {
            subscribed = perDao.isUserSubscribed(uuid, per);
        } catch (DaoException e) {
           /*TODO log*/
        }
        return subscribed;
    }
}
