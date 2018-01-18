package com.periodicals.services;

import com.periodicals.command.auth.PeriodicalIssuesCommand;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PeriodicalsJdbcDao;
import com.periodicals.entities.Genre;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PeriodicalService {
    private static final Logger LOGGER = Logger.getLogger(PeriodicalService.class.getSimpleName());

    private static PeriodicalService periodicalService = new PeriodicalService();

    private static PeriodicalsJdbcDao perDao =
            (PeriodicalsJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalsDao();

    private PeriodicalService() {

    }

    public static PeriodicalService getInstance() {
        return periodicalService;
    }

    public List<Periodical> getAllPeriodicals() {
        List<Periodical> result = new ArrayList<>();
        try {
            result = perDao.getAll();
            LOGGER.debug("Obtained all periodicals using DAO");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain all periodicals using DAO: " + e.getMessage());
        }
        return result;
    }

    public Periodical getPeriodicalById(int id) {
        Periodical result = null;
        try {
            result = perDao.getById(id);
            LOGGER.debug("Obtained periodical by id " + id);
        } catch (DaoException e) {
            LOGGER.debug("Failed to obtain periodical by id "+ id +" using DAO: " + e.getMessage());
        }
        return result;
    }

    public void delete(Periodical periodical) throws ServiceException {
        try {
            perDao.delete(periodical);
            LOGGER.debug("Successful deletion of periodical with id " + periodical.getId());
        } catch (DaoException e) {
            LOGGER.error("Failed to delete periodical using DAO: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    public void update(Periodical periodical) throws ServiceException {
        try {
            perDao.update(periodical);
            LOGGER.debug("Successful update of periodical with id " + periodical.getId());
        } catch (DaoException e) {
            LOGGER.error("Failed to update periodical using DAO: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    public void add(Periodical periodical) throws ServiceException {
        try {
            perDao.add(periodical);
            LOGGER.debug("Successful periodical adding");
        } catch (DaoException e) {
            LOGGER.error("Failed to add periodical using DAO: " + e.getMessage());
            throw new ServiceException(e);
        }
    }

    public List<Periodical> getPeriodicalsSublist(int skip, int take) {
        List<Periodical> periodicals = new ArrayList<>();
        try {
            periodicals = perDao.getPeriodicalSubList(skip, take);
            LOGGER.debug("Obtained periodicals sublist");
        } catch (DaoException e) {
            LOGGER.error("Failed to get periodicals sublist using DAO: " + e.getMessage());
        }
        return periodicals;
    }

    public long getPeriodicalsCount() {
        long result = 0;
        try {
            result = perDao.getPeriodicalsCount();
            LOGGER.debug("Obtained all periodicals count");
        } catch (DaoException e) {
            LOGGER.error("Failed to get periodicals count using DAO: " + e.getMessage());
        }
        return result;
    }

    public List<Periodical> getGenrePeriodicalsSublist(Genre genre, int skip, int take) {
        List<Periodical> periodicals = new ArrayList<>();
        try {
            periodicals = perDao.getGenrePeriodicalsSublist(genre, skip, take);
            LOGGER.debug("Obtained genre periodicals count");
        } catch (DaoException e) {
            LOGGER.error("Failed to get genre periodicals sublist using DAO: " + e.getMessage());
        }
        return periodicals;
    }

    public List<Periodical> getPaymentPeriodicals(Payment payment) throws ServiceException {
        List<Periodical> payPeriodicals = new ArrayList<>();
        try {
            payPeriodicals = perDao.getPaymentPeriodicals(payment);
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain payment periodicals using DAO: " + e.getMessage());
            throw new ServiceException(e);
        }
        return payPeriodicals;
    }

    public long getGenrePeriodicalsCount(Genre genre) {
        long result = 0;
        try {
            result = perDao.getGenrePeriodicalCount(genre);
            LOGGER.debug("Obtained genre periodicals count: " + result);
        } catch (DaoException e) {
            LOGGER.error("Failed to get genre periodicals count using DAO: " + e.getMessage());
        }
        return result;
    }
}
