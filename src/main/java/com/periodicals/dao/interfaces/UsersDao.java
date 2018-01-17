package com.periodicals.dao.interfaces;

import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;

import java.util.List;

public interface UsersDao extends GenericDao<User, String> {
    User getByLogin(String login) throws DaoException;
    long getEntriesCount() throws DaoException;
    List<User> getSublist(int skip, int take) throws DaoException;
}
