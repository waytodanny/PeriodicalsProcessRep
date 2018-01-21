package com.periodicals.dao.interfaces;

import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;

import java.util.List;

public interface UsersDao extends GenericDao<User, String> {
    User getByLogin(String login) throws DaoException;
    int getUsersCount() throws DaoException;
    List<User> geUsersLimited(int skip, int limit) throws DaoException;
}
