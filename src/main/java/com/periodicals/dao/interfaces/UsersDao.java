package com.periodicals.dao.interfaces;

import com.periodicals.dao.interfaces.GenericDao;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;

public interface UsersDao extends GenericDao<User, String> {
    User getUserByLogin(String login) throws DaoException;
}
