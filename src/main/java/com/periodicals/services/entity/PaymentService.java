package com.periodicals.services.entity;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PaymentsJdbcDao;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.services.util.PageableCollectionService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PaymentService implements PageableCollectionService<Payment> {
    private static final Logger LOGGER = Logger.getLogger(PaymentService.class.getSimpleName());

    private static final PaymentsJdbcDao paymentDao =
            (PaymentsJdbcDao) JdbcDaoFactory.getInstance().getPaymentsDao();

    private static final PaymentService paymentService = new PaymentService();
    private static final UserService userService = UserService.getInstance();
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();


    private PaymentService() {
    }

    public static PaymentService getInstance() {
        return paymentService;
    }

    public List<Payment> getEntityCollection() {
        List<Payment> result = new ArrayList<>();
        try {
            result = paymentDao.getEntityCollection();
            LOGGER.debug("Obtained all payments using DAO");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain all payments using DAO: " + e.getMessage());
        }
        return result;
    }

    public Payment getEntityByPrimaryKey(UUID id) {
        Payment result = null;
        try {
            result = paymentDao.getEntityByPrimaryKey(id);
            LOGGER.debug("Obtained payment by id " + id);
        } catch (DaoException e) {
            LOGGER.debug("Failed to obtain payment by id " + id + " using DAO: " + e.getMessage());
        }
        return result;
    }

    public void createEntity(Timestamp paymentTime, UUID userId, BigDecimal paymentSum, List<UUID> periodicals)
            throws ServiceException {
        try {
            Payment added = new Payment();
            added.setPaymentTime(paymentTime);
            added.setPaymentSum(paymentSum);

            User addedUser = userService.getEntityByPrimaryKey(userId);
            if (Objects.nonNull(addedUser)) {
                added.setUserId(addedUser.getId());
            } else {
                throw new NullPointerException("User with id " + userId + " doesn't exist");
            }

            List<Periodical> addedPeriodicals = new ArrayList<>();
            for (UUID periodicalId : periodicals) {
                Periodical periodical = periodicalService.getEntityByPrimaryKey(periodicalId);
                if (Objects.nonNull(periodical)) {
                    addedPeriodicals.add(periodical);
                } else {
                    throw new NullPointerException("Periodical with id " + periodicalId + " doesn't exist");
                }
            }
            added.setPeriodicals(addedPeriodicals);

            paymentDao.createEntity(added);
            LOGGER.debug("Successful payment adding");
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    /*
     *   UPDATE and DELETE methods are not available for entity PAYMENT
     */

    @Override
    public int getEntitiesCount() {
        int result = 0;
        try {
            result = paymentDao.getEntitiesCount();
            LOGGER.debug("Obtained all payments count");
        } catch (DaoException e) {
            LOGGER.error("Failed to get payments count using DAO: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Payment> getEntitiesListBounded(int skip, int limit) {
        List<Payment> entities = new ArrayList<>();
        try {
            entities = paymentDao.getPaymentsListBounded(skip, limit);
            LOGGER.debug("Obtained payments bounded list");
        } catch (DaoException e) {
            LOGGER.error("Failed to get payments bounded list using DAO: " + e.getMessage());
        }
        return entities;
    }

    public List<Payment> getUserPaymentsListBounded(int skip, int take, UUID userId) {
        List<Payment> payments = new ArrayList<>();
        try {
            User user = userService.getEntityByPrimaryKey(userId);
            if (Objects.nonNull(user)) {
                payments = paymentDao.getUserPaymentsListBounded(user, skip, take);
                LOGGER.debug("Obtained user payments bounded list");
            } else {
                throw new NullPointerException("User with id " + userId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return payments;
    }

    public int getUserPaymentsCount(UUID userId) throws ServiceException {
        try {
            User user = userService.getEntityByPrimaryKey(userId);
            if (Objects.nonNull(user)) {
                int result = paymentDao.getUserPaymentsCount(user);
                LOGGER.debug("Obtained user payments count");
                return result;
            } else {
                throw new NullPointerException("User with id " + userId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }
}
