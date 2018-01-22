package com.periodicals.dao.interfaces;

import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;

import java.util.List;
import java.util.UUID;

public interface UsersDao extends GenericDao<User, UUID> {
    User getByLogin(String login) throws DaoException;
    int getUsersCount() throws DaoException;
    List<User> geUsersLimitedList(int skip, int limit) throws DaoException;
}
