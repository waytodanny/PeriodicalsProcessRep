package com.periodicals.dao.interfaces;

import com.periodicals.dao.entities.Genre;
import com.periodicals.dao.entities.Payment;
import com.periodicals.dao.entities.Periodical;
import com.periodicals.exceptions.DaoException;

import java.util.List;

public interface PeriodicalsDao extends GenericDao<Periodical, Integer> {

    boolean isUserSubscribed(String uuid, Periodical per) throws DaoException;

    List<Periodical> getUserSubscriptions(String userUuid) throws DaoException;

    void addUserSubscriptions(String uuid, List<Periodical> subs) throws DaoException;

    List<Periodical> getGenrePeriodicalsSublist(Genre genre, int skip, int take) throws DaoException;

    int getGenrePeriodicalCount(Genre genre) throws DaoException;

    /*to take first 5 object args must be: (0, 5)*/
    List<Periodical> getPeriodicalSubList(int skip, int take) throws DaoException;

    int getPeriodicalsCount() throws DaoException;

    int getUserSubscriptionsCount(String userId) throws DaoException;

    List<Periodical> getPaymentPeriodicals(Payment payment) throws DaoException;
}
