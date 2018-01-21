package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.UsersJdbcDao;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for communicating with UsersDao implementors to obtain from or provide data to DB
 *
 * @see UsersJdbcDao
 */
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getSimpleName());

    private static UserService userService = new UserService();
    private static UsersJdbcDao usersDao =
            (UsersJdbcDao) JdbcDaoFactory.getInstance().getUsersDao();

    private UserService() {

    }

    public static UserService getInstance() {
        return userService;
    }

    /**
     * Gets user from DB by login and returns it or null if it's not found
     */
    public User getUserByLogin(String login) {
        User user = null;
        try {
            user = usersDao.getByLogin(login);
            LOGGER.debug("Succeed to find user by login: " + login);
        } catch (DaoException e) {
            LOGGER.debug("Failed to find user by login: " + login + ", " + e.getMessage());
        }
        return user;
    }

    public void delete(User user) {
        try {
            usersDao.delete(user);
        } catch (DaoException e) {
            LOGGER.debug("Failed to find user: " + e.getMessage());
        }
    }

    public void update(User user) {
        try {
            usersDao.update(user);
        } catch (DaoException e) {
            LOGGER.debug("Failed to update user" + e.getMessage());
        }
    }

    public User getUserById(String id) {
        User result = null;
        try {
            result = usersDao.getById(id);
        } catch (DaoException e) {
            LOGGER.debug("Failed to get user by id: " + id + e.getMessage());
        }
        return result;
    }

    public long getUsersCount() {
        long result = 0;
        try {
            result = usersDao.getUsersCount();
        } catch (DaoException e) {
            LOGGER.debug("Failed to get users count: " + e.getMessage());
        }
        return result;
    }

    public List<User> getUsersSublist(int skip, int take) {
        List<User> sublist = new ArrayList<>();
        try {
            sublist = usersDao.geUsersLimited(skip, take);
        } catch (DaoException e) {
            LOGGER.debug("Failed to get users sublist: " + e.getMessage());
        }
        return sublist;
    }
}
