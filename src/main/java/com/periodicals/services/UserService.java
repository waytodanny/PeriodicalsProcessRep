package com.periodicals.services;

import com.periodicals.dao.entities.User;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.UsersJdbcDao;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static UserService userService = new UserService();
    private static UsersJdbcDao usersDao =
            (UsersJdbcDao) JdbcDaoFactory.getInstance().getUsersDao();
    private static RoleService roleService = RoleService.getInstance();

    private UserService() {

    }

    public static UserService getInstance() {
        return userService;
    }

    public void delete(String uuid) {
        try {
            usersDao.delete(uuid);
        } catch (DaoException e) {
            /*TODO log*/
        }
    }

    public void update(User user) {
        try {
            usersDao.update(user);
        } catch (DaoException e) {
            /*TODO log*/
        }
    }

    public User getUserById(String id) throws ServiceException {
        User result = null;
        try {
            result = usersDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException("not found user by id");
        }
        return result;
    }

    public int getUsersCount() {
        int result = 0;
//        try {
////            result = usersDao.getUsersCount();
//        } catch (DaoException e) {
//            /*TODO log*/
//        }
        return result;
    }

    public List<User> getUsersSublist(int skip, int take) {
        List<User> dtoList = new ArrayList<>();
//        try {
//            List<User> entityList = usersDao.getSublist(skip, take);
//            fillPeriodicals(entityList, dtoList);
//        } catch (DaoException e) {
//            /*TODO log*/
//        }
        return dtoList;
    }
}
