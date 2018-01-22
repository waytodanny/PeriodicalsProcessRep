package com.periodicals.services.entity;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.UsersJdbcDao;
import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.services.lookup.RoleService;
import com.periodicals.services.util.PageableCollectionService;
import com.periodicals.utils.encryption.Cryptographer;
import com.periodicals.utils.encryption.MD5Cryptographer;
import com.periodicals.utils.uuid.UuidGenerator;
import org.apache.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserService implements PageableCollectionService<User> {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getSimpleName());

    private static final UserService userService = new UserService();
    private static final RoleService roleService = RoleService.getInstance();

    private static UsersJdbcDao userDao = (UsersJdbcDao) JdbcDaoFactory.getInstance().getUsersDao();

    private UserService() { }

    public static UserService getInstance() {
        return userService;
    }

    public List<User> getEntityCollection() {
        List<User> result = new ArrayList<>();
        try {
            result = userDao.getEntityCollection();
            LOGGER.debug("Obtained all users using DAO");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain all users using DAO: " + e.getMessage());
        }
        return result;
    }

    public User getEntityByPrimaryKey(UUID id) {
        User result = null;
        try {
            result = userDao.getEntityByPrimaryKey(id);
            LOGGER.debug("Obtained user by id " + id);
        } catch (DaoException e) {
            LOGGER.debug("Failed to obtain user by id "+ id +" using DAO: " + e.getMessage());
        }
        return result;
    }

    public void createEntity(String login, String password, String email, UUID roleId) throws ServiceException {
        try {
            User added = new User();
            added.setId(UuidGenerator.generateUuid());
            added.setLogin(login);
            added.setEmail(email);

            Cryptographer cryptographer = new MD5Cryptographer();
            added.setPassword(cryptographer.encrypt(password));

            Role addedRole = roleService.getEntityByPrimaryKey(roleId);
            if(Objects.nonNull(addedRole)) {
                added.setRole(addedRole);
            } else {
                throw new NullPointerException("Role with id " + roleId + " doesn't exist");
            }

            userDao.createEntity(added);
            LOGGER.debug("User's registration succeeded");
        } catch (DaoException | NullPointerException | NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    public void updateEntity(UUID id, UUID roleId) throws ServiceException {
        try {
            User updated = this.getEntityByPrimaryKey(id);
            if(Objects.nonNull(updated)) {

                Role updatedRole = roleService.getEntityByPrimaryKey(roleId);
                if(Objects.nonNull(updatedRole)) {
                    updated.setRole(updatedRole);
                } else {
                    throw new NullPointerException("Role with id " + roleId + " doesn't exist");
                }

                userDao.updateEntity(updated);
            } else {
                throw new NullPointerException("User with id " + id + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    public void deleteEntity(UUID id) throws ServiceException {
        try {
            User deleted = this.getEntityByPrimaryKey(id);
            if(Objects.nonNull(deleted)) {
                userDao.deleteEntity(deleted.getId());
                LOGGER.debug("Successful deletion of user with id " + id);
            } else {
                throw new NullPointerException("User with id " + id + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getEntitiesListBounded(int skip, int limit) {
        List<User> entities = new ArrayList<>();
        try {
            entities = userDao.geUsersLimitedList(skip, limit);
            LOGGER.debug("Obtained users bounded list");
        } catch (DaoException e) {
            LOGGER.error("Failed to get users bounded list using DAO: " + e.getMessage());
        }
        return entities;
    }

    @Override
    public int getEntitiesCount() {
        int result = 0;
        try {
            result = userDao.getEntitiesCount();
            LOGGER.debug("Obtained all users count");
        } catch (DaoException e) {
            LOGGER.error("Failed to get users count using DAO: " + e.getMessage());
        }
        return result;
    }

    public User getUserByLogin(String login) {
        User user = null;
        try {
            user = userDao.getByLogin(login);
            if(Objects.nonNull(user)) {
                LOGGER.debug("Succeeded to find user by login: " + login);
            } else {
                throw new NullPointerException("User with login " + login + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return user;
    }
}
