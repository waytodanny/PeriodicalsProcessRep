package com.periodicals.dao.factories;

import com.periodicals.dao.interfaces.*;
import com.periodicals.dao.jdbc.*;

/**
 * JDBC Singleton factory class that implements AbstractDaoFactory
 *
 * @author Daniel Volnitsky
 * @see AbstractDaoFactory
 */
public class JdbcDaoFactory extends AbstractDaoFactory {
    private static JdbcDaoFactory instance = new JdbcDaoFactory();

    private static RolesDao rolesDao = new RolesJdbcDao();
    private static UsersDao usersDao = new UsersJdbcDao();
    private static PaymentsDao paymentsDao = new PaymentsJdbcDao();
    private static GenresDao genresDao = new GenresJdbcDao();
    private static PeriodicalIssuesDao periodicalIssuesDao = new PeriodicalIssuesJdbcDao();
    private static PeriodicalsDao periodicalsDao = new PeriodicalsJdbcDao();
    private static PublishersDao publishersDao = new PublishersJdbcDao();

    /*TODO delete*/
//    private static ComplexQueryJdbcDao complexQueryDAO = new ComplexQueryJdbcDao();

    private JdbcDaoFactory() {

    }

    public static JdbcDaoFactory getInstance() {
        return instance;
    }

    @Override
    public RolesDao getRolesDao() {
        return rolesDao;
    }

    @Override
    public UsersDao getUsersDao() {
        return usersDao;
    }

    @Override
    public GenresDao getGenresDao() {
        return genresDao;
    }

    @Override
    public PeriodicalsDao getPeriodicalsDao() {
        return periodicalsDao;
    }

    @Override
    public PeriodicalIssuesDao getPeriodicalIssuesDao() {
        return periodicalIssuesDao;
    }

    @Override
    public PublishersDao getPublishersDao() {
        return publishersDao;
    }

    @Override
    public PaymentsDao getPaymentsDao() {
        return paymentsDao;
    }

//    public ComplexQueryJdbcDao getComplexQueryDAO() {
//        return complexQueryDAO;
//    }
}

