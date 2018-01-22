package com.periodicals.services;

import com.periodicals.entities.User;
import com.periodicals.services.entity.UserService;
import com.periodicals.utils.encryption.MD5Cryptographer;
import org.apache.log4j.Logger;

import java.util.Objects;

public class LoginService {
    private static final Logger LOGGER = Logger.getLogger(LoginService.class.getSimpleName());

    private static LoginService loginService = new LoginService();
    private static UserService userService = UserService.getInstance();

    private LoginService() {

    }

    public static LoginService getInstance() {
        return loginService;
    }

    /**
     * @return user object from DB if he was verified by incoming data or null if not
     */
    public User getUserIfVerified(String login, String pass) {
        User user = userService.getUserByLogin(login);
        if (Objects.nonNull(user) && passwordsMatch(user, pass)) {
            LOGGER.debug("user has been verified");
            return user;
        }
        return null;
    }

    /**
     * Checks that user brought from DB has same password that client passed
     *
     * @param user     user from DB taken by login
     * @param password password that was entered by client
     */
    private boolean passwordsMatch(User user, String password) {
        boolean result = false;
        if (Objects.nonNull(password) && Objects.nonNull(user) && Objects.nonNull(user.getPassword())) {
            String encrypted = new MD5Cryptographer().encrypt(password);
            result = Objects.nonNull(encrypted) && encrypted.equals(user.getPassword());
            LOGGER.debug("passwords match");
        }
        return result;
    }
}
