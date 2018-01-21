package com.periodicals.dao.interfaces;

import com.periodicals.entities.Genre;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;

import java.util.List;

public interface PeriodicalsDao extends GenericDao<Periodical, String> {
    boolean isUserSubscribed(User user, Periodical per) throws DaoException;

    List<Periodical> getUserSubscriptions(User user) throws DaoException;

    void addUserSubscriptions(User user, List<Periodical> subscriptions) throws DaoException;

    List<Periodical> getGenrePeriodicalsLimited(Genre genre, int skip, int limit) throws DaoException;

    int getGenrePeriodicalsCount(Genre genre) throws DaoException;

    List<Periodical> getPeriodicalsLimited(int skip, int limit) throws DaoException;

    int getPeriodicalsCount() throws DaoException;

    int getUserSubscriptionsCount(User user) throws DaoException;

    List<Periodical> getPaymentPeriodicals(Payment payment) throws DaoException;
}
