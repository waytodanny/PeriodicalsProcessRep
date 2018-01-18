package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.UsersJdbcDao;
import com.periodicals.entities.User;
import com.periodicals.exceptions.RegistrationException;
import com.periodicals.utils.encryption.Cryptographer;
import com.periodicals.utils.encryption.MD5_Cryptographer;
import com.periodicals.utils.uuid.UuidGenerator;
import org.apache.log4j.Logger;

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

    private RegistrationService() {

    }

    public static RegistrationService getInstance() {
        return registrationService;
    }

    public void register(String login, String pass, String email) throws RegistrationException {
        /*TODO discover if it's needed to check params for emptiness there after command did it*/
//        String[] reqFields = {login, pass, email};
//        if (requiredFieldsNotEmpty(reqFields)) {
        try {
            User newUser = new User();

            String uuid = UuidGenerator.generateUuid();
            newUser.setId(uuid);
            newUser.setLogin(login);
            newUser.setEmail(email);
            newUser.setRole(RoleService.USER_ROLE);

            Cryptographer cryptographer = new MD5_Cryptographer();
            newUser.setPassword(cryptographer.encrypt(pass));

            /*TODO discover if it's needed to add via UserService*/
            usersDao.add(newUser);
            LOGGER.debug("User's registration succeed");
        } catch (Exception e) {
            LOGGER.debug("User failed to register: " + e.getMessage());
            throw new RegistrationException(e);
        }
//        } else
//            throw new RegistrationException("Required fields must not be empty");
    }
}
