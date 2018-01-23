package com.periodicals.services.entities;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PaymentsJdbcDao;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.services.interfaces.LookupService;
import com.periodicals.services.interfaces.PageableCollectionService;
import com.periodicals.utils.UUIDHelper;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author Daniel Volnitsky
 * <p>
 * Service responsible for processing payment-related operations
 */
public class PaymentService implements PageableCollectionService<Payment>, LookupService<Payment, UUID> {
    private static final Logger LOGGER = Logger.getLogger(PaymentService.class.getSimpleName());

    private static final PaymentService paymentService = new PaymentService();
    private static final UserService userService = UserService.getInstance();
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    private static final PaymentsJdbcDao paymentsDao =
            (PaymentsJdbcDao) JdbcDaoFactory.getInstance().getPaymentsDao();

    public static PaymentService getInstance() {
        return paymentService;
    }

    @Override
    public Payment getEntityByPrimaryKey(UUID id) {
        Payment result = null;
        try {
            result = paymentsDao.getEntityByPrimaryKey(id);
            LOGGER.debug("Obtained payment with id " + id);
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain payment with id " + id + " due to: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Payment> getEntityCollection() {
        List<Payment> entities = new ArrayList<>();
        try {
            entities = paymentsDao.getEntityCollection();
            LOGGER.debug("Obtained all payments");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain all payments: " + e.getMessage());
        }
        return entities;
    }

    /**
     * Constructs and inserts Payment-object
     *
     * @param periodicals list of periodicals that were included in payment
     */
    public void createEntity(User user, BigDecimal paymentSum, List<Periodical> periodicals)
            throws ServiceException {
        try {
            Payment added = new Payment();
            UUID id = UUIDHelper.generateSequentialUuid();

            added.setId(id);
            added.setPaymentTime(new Timestamp(System.currentTimeMillis()));
            added.setPaymentSum(paymentSum);

            /*
            User addedUser = userService.getEntityByPrimaryKey(userId);
            if (Objects.nonNull(addedUser)) {
                added.setUser(addedUser);
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
            */

            added.setPeriodicals(periodicals);
            paymentsDao.createEntity(added);
            LOGGER.debug("Payment with id " + id + " has been successfully created");
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Payment> getEntitiesListBounded(int skip, int limit) {
        List<Payment> entities = new ArrayList<>();
        try {
            entities = paymentsDao.getEntitiesListBounded(skip, limit);
            LOGGER.debug("Obtained payments bounded list");
        } catch (DaoException e) {
            LOGGER.error("Failed to get payments bounded list: " + e.getMessage());
        }
        return entities;
    }

    @Override
    public int getEntitiesCount() {
        int result = 0;
        try {
            result = paymentsDao.getEntitiesCount();
            LOGGER.debug("Obtained all payments count");
        } catch (DaoException e) {
            LOGGER.error("Failed to get all payments count: " + e.getMessage());
        }
        return result;
    }

    /**
     * @param userId id of user whose payments intended to be obtained
     * @return user limited list of payments
     */
    public List<Payment> getPaymentsByUserListBounded(int skip, int limit, UUID userId) {
        List<Payment> entities = new ArrayList<>();
        try {
            User user = userService.getEntityByPrimaryKey(userId);
            if (Objects.nonNull(user)) {
                entities = paymentsDao.getPaymentsByUserListBounded(skip, limit, user);
                LOGGER.debug("Obtained payments bounded list with userId " + userId);
            } else {
                throw new NullPointerException("User with id " + userId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return entities;
    }

    /**
     * @param userId id of user whose payments count intended to be obtained
     * @return count of specified user payments
     */
    public int getPaymentsByUserCount(UUID userId) {
        int result = 0;
        try {
            User user = userService.getEntityByPrimaryKey(userId);
            if (Objects.nonNull(user)) {
                result = paymentsDao.getPaymentsByUserCount(user);
                LOGGER.debug("Obtained payments list count with userId " + userId);
            } else {
                throw new NullPointerException("User with id " + userId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }
}
