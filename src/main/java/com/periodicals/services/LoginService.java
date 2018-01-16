package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.UsersJdbcDao;
import com.periodicals.dto.RoleDto;
import com.periodicals.dto.UserDto;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
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

    public UserDto getIfVerified(String login, String pass) {
        User entity = getUser(login);
        if (isVerified(entity, pass)) {
            UserDto dto;
            try {
                dto = getDtoOfEntity(entity);
            } catch (ServiceException e) {
                return null;
            }
            return dto;
        }
        return null;
    }

    /*TODO вынести логику роли*/
    private UserDto getDtoOfEntity(User entity) throws ServiceException {
        UserDto userDto = new UserDto();

        userDto.setUuid(entity.getId());
        userDto.setLogin(entity.getLogin());
        userDto.setPassword(entity.getPassword());
        userDto.setEmail(entity.getEmail());

        RoleDto roleDto = roleService.getRoleDtoById(entity.getRoleId());
        userDto.setRole(roleDto);

        return userDto;
    }

    private User getUser(String login) {
        User user = null;
        try {
            user = usersDao.getUserByLogin(login);
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
