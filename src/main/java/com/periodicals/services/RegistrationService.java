package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.UsersJdbcDao;
import com.periodicals.dao.entities.util.Role;
import com.periodicals.dao.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.RegistrationException;
import com.periodicals.utils.encryption.MD5_Cryptographer;
import com.periodicals.utils.uuid.UuidGenerator;
import org.apache.log4j.Logger;

import static com.periodicals.command.util.CommandHelper.requiredFieldsNotEmpty;
import static com.periodicals.utils.AttributesHolder.ROLE_USER;

/**
 * Service responsible for user registration
 *
 * @see UsersJdbcDao
 */
public class RegistrationService {
    private static final Logger LOGGER = Logger.getLogger(RegistrationService.class.getSimpleName());

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
        String[] reqFields = {login, pass, email};
        if (requiredFieldsNotEmpty(reqFields)) {
            try {
                User user = new User();

                String uuid = UuidGenerator.generateUuid();
                user.setId(uuid);
                user.setLogin(login);
                user.setEmail(email);

                Role role = roleService.getRole(ROLE_USER);

//                user.setRoleId(role.getId());
                user.setPassword(new MD5_Cryptographer().encrypt(pass));

                usersDao.add(user);

                LOGGER.debug("User's registration succeed");
            } catch (DaoException | NullPointerException e) {
                LOGGER.debug("User failed to register: " + e.getMessage());
                throw new RegistrationException(e);
            }
        } else
            throw new RegistrationException("Required fields must not be empty");
    }
}
