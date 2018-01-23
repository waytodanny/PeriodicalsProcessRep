package com.periodicals.dao.factories;

import com.periodicals.dao.interfaces.*;

/**
 * @author Daniel Volnitsky 24.12.2017
 *
 * Abstract Dao factory that specifies getters for all Dao factory inheritors
 */
public abstract class AbstractDaoFactory {
    public abstract RolesDao getRolesDao();

    public abstract UsersDao getUsersDao();

    public abstract GenresDao getGenresDao();

    public abstract PeriodicalsDao getPeriodicalsDao();

    public abstract PeriodicalIssuesDao getPeriodicalIssuesDao();

    public abstract PublishersDao getPublishersDao();

    public abstract PaymentsDao getPaymentsDao();
}