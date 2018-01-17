package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.UsersJdbcDao;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.encryption.MD5_Cryptographer;

import java.util.Objects;

public class LoginService {
    private static LoginService loginService = new LoginService();
    private static RoleService roleService = RoleService.getInstance();
    private static UsersJdbcDao usersDao =
            (UsersJdbcDao) JdbcDaoFactory.getInstance().getUsersDao();

    private LoginService() {

    }

    public static LoginService getInstance() {
        return loginService;
    }

    public User getUserIfVerified(String login, String pass) {
        User user = getUser(login);
        if (isVerified(user, pass)) {
            return user;
        }
        return null;
    }

    private User getUser(String login) {
        User user = null;
        try {
            user = usersDao.getByLogin(login);
        } catch (DaoException e) {
            /*TODO LOG*/
        }
        return user;
    }

    private boolean isVerified(User user, String password) {
        boolean result = false;
        if (Objects.nonNull(password) && Objects.nonNull(user) && Objects.nonNull(user.getPassword())) {
            String encrypted = new MD5_Cryptographer().encrypt(password);
            result = Objects.nonNull(encrypted) && encrypted.equals(user.getPassword());
        }
        return result;
    }
}
