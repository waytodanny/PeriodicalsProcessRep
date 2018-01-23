package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PeriodicalsJdbcDao;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.services.entities.PeriodicalService;
import com.periodicals.services.entities.UserService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 * <p>
 * Service responsible for providing information about user subscriptions
 */
public class SubscriptionService {
    private static final Logger LOGGER = Logger.getLogger(SubscriptionService.class.getSimpleName());

    private static final SubscriptionService subscriptionService = new SubscriptionService();
    private static final UserService userService = UserService.getInstance();
    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();

    private static final PeriodicalsJdbcDao periodicalsDao =
            (PeriodicalsJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalsDao();

    public static SubscriptionService getInstance() {
        return subscriptionService;
    }

    /**
     * Gets list of periodicals that user subscribed on
     *
     * @param userId user id
     */
    public List<Periodical> getPeriodicalsByUserList(UUID userId) {
        List<Periodical> entities = new ArrayList<>();
        try {
            User user = userService.getEntityByPrimaryKey(userId);
            if (Objects.nonNull(user)) {
                entities = periodicalsDao.getPeriodicalsByUserList(user);
                LOGGER.debug("Obtained user periodicals list");
            } else {
                throw new NullPointerException("User with id " + userId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return entities;
    }

    /**
     * Gets number of user subscriptions
     *
     * @param userId user id
     */
    public int getPeriodicalsByUserCount(UUID userId) {
        int result = 0;
        try {
            User user = userService.getEntityByPrimaryKey(userId);
            if (Objects.nonNull(user)) {
                result = periodicalsDao.getPeriodicalsByUserCount(user);
                LOGGER.debug("Obtained user periodicals list count");
            } else {
                throw new NullPointerException("User with id " + userId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    /**
     * Gets number of user subscriptions
     *
     * @param userId user id
     */
    public List<Periodical> getPeriodicalsByUserListBounded(int skip, int limit, UUID userId) {
        List<Periodical> entities = new ArrayList<>();
        try {
            User user = userService.getEntityByPrimaryKey(userId);
            if (Objects.nonNull(user)) {
                entities = periodicalsDao.getPeriodicalsByUserListBounded(skip, limit, user);
                LOGGER.debug("Obtained user periodicals list bounded");
            } else {
                throw new NullPointerException("User with id " + userId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return entities;
    }

    /**
     * Informs whether user subscribed on periodical or not
     *
     * @param userId       user id
     * @param periodicalId checking for subscription periodical id
     * @return is user subscribed on given periodical
     */
    public boolean getIsUserSubscribedForPeriodical(UUID userId, UUID periodicalId) {
        boolean result = false;
        try {
            User user = userService.getEntityByPrimaryKey(userId);
            if (Objects.nonNull(user)) {
                LOGGER.debug("Obtained user with id " + userId);
            } else {
                throw new NullPointerException("User with id " + userId + " doesn't exist");
            }

            Periodical periodical = periodicalService.getEntityByPrimaryKey(periodicalId);
            if (Objects.nonNull(periodical)) {
                LOGGER.debug("Obtained periodical with id " + periodicalId);
            } else {
                throw new NullPointerException("Periodical with id " + periodicalId + " doesn't exist");
            }

            result = periodicalsDao.getIsUserSubscribedForPeriodical(user, periodical);
            LOGGER.debug("Succeed to get whether user with id " + user.getId() +
                    " subscribed on periodical with id " + periodicalId);
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }
}
