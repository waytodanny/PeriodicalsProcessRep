package com.periodicals.services;

import com.periodicals.dao.factories.AbstractDaoFactory;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.UsersJdbcDao;
import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.RegistrationException;
import com.periodicals.utils.encryption.MD5_Cryptographer;
import com.periodicals.utils.uuid.UuidGenerator;

import static com.periodicals.utils.AttributesHolder.ROLE_USER;
import static com.periodicals.utils.AttributesHolder.USER;

public class RegistrationService {
    private static RegistrationService registrationService = new RegistrationService();
    private static UsersJdbcDao usersDao =
            (UsersJdbcDao) JdbcDaoFactory.getInstance().getUsersDao();

    private static RoleService roleService = RoleService.getInstance();

    private RegistrationService() {

    }

    public static RegistrationService getInstance() {
        return registrationService;
    }

    public void register(String login, String pass, String email) throws RegistrationException {
        User user = new User();
        user.setId(UuidGenerator.generateUuid());
        user.setLogin(login);
        user.setEmail(email);

        Role role = roleService.getRole(ROLE_USER);

        user.setRoleId(role.getId());
        user.setPassword(new MD5_Cryptographer().encrypt(pass));
        try {
            usersDao.add(user);
        } catch (DaoException e) {
            throw new RegistrationException("User with same credentials already exists");
        }
    }
}
