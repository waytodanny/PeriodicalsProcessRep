package com.periodicals.dao.interfaces;

import com.periodicals.dao.entities.Genre;
import com.periodicals.dao.entities.Payment;
import com.periodicals.dao.entities.Periodical;
import com.periodicals.dao.entities.User;
import com.periodicals.exceptions.DaoException;

import java.util.List;

public interface PeriodicalsDao extends GenericDao<Periodical, Integer> {

    boolean isUserSubscribed(User user, Periodical per) throws DaoException;

    List<Periodical> getUserSubscriptions(User user) throws DaoException;

    void addUserSubscriptions(User user, List<Periodical> subs) throws DaoException;

    List<Periodical> getGenrePeriodicalsSublist(Genre genre, int skip, int take) throws DaoException;

    long getGenrePeriodicalCount(Genre genre) throws DaoException;

    /*to take first 5 object args must be: (0, 5)*/
    List<Periodical> getPeriodicalSubList(int skip, int take) throws DaoException;

    long getPeriodicalsCount() throws DaoException;

    long getUserSubscriptionsCount(User user) throws DaoException;

    List<Periodical> getPaymentPeriodicals(Payment payment) throws DaoException;
}
