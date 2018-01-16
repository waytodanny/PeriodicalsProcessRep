package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.UsersJdbcDao;
import com.periodicals.dto.RoleDto;
import com.periodicals.dto.UserDto;
import com.periodicals.dao.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static UserService userService = new UserService();
    private static UsersJdbcDao usersDao =
            (UsersJdbcDao) JdbcDaoFactory.getInstance().getUsersDao();

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
            result = usersDao.getByKey(id);
        } catch (DaoException e) {
           throw new ServiceException("not found user by id");
        }
        return result;
    }

    public int getUsersCount() {
        int result = 0;
        try {
            result = usersDao.getUsersCount();
        } catch (DaoException e) {
            /*TODO log*/
        }
        return result;
    }

    public List<UserDto> getUsersDtoSublist(int skip, int take) {
        List<UserDto> dtoList = new ArrayList<>();
        try {
            List<User> entityList = usersDao.getUsersSublist(skip, take);
            fillPeriodicalsDto(entityList, dtoList);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return dtoList;
    }

    private static void fillPeriodicalsDto(List<User> entityList, List<UserDto> dtoList) {
        for (User entity : entityList) {
            UserDto dto = getDtoByEntity(entity);
            dtoList.add(dto);
        }
    }

    /*TODO checking for null*/
    private static UserDto getDtoByEntity(User entity) {
        UserDto dto = new UserDto();

        dto.setUuid(entity.getId());
        dto.setLogin(entity.getLogin());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        try {
            RoleDto role = RoleService.getInstance().getRoleDtoById(entity.getRoleId());
            dto.setRole(role);
        } catch (ServiceException e) {
            /*TODO log*/
        }
        return dto;
    }
}
