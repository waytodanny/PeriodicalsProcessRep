package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PeriodicalsJdbcDao;
import com.periodicals.entities.Genre;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class PeriodicalService {
    private static PeriodicalService periodicalService = new PeriodicalService();

    private static PeriodicalsJdbcDao perDao =
            (PeriodicalsJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalsDao();

    private static GenresService genresService = GenresService.getInstance();

    private PeriodicalService() {

    }

    public static PeriodicalService getInstance() {
        return periodicalService;
    }

    public List<Periodical> getAllPeriodicals() {
        List<Periodical> result = null;
        try {
            result = perDao.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Periodical getPeriodicalById(int id) {
        Periodical result = null;
        try {
            result = perDao.getById(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void delete(int id) {
        try {
            perDao.delete(new Periodical()/*id*/);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void update(Periodical periodical) {
        try {
            perDao.update(periodical);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void add(Periodical periodical) throws Exception {
        try {
            perDao.add(periodical);
        } catch (DaoException e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Periodical> getPeriodicalsSublist(int skip, int take) {
        List<Periodical> periodicals = new ArrayList<>();
        try {
            periodicals = perDao.getPeriodicalSubList(skip, take);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return periodicals;
    }

    public long getPeriodicalsCount() {
        long result = 0;
        try {
            result = perDao.getPeriodicalsCount();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Periodical> getGenrePeriodicalsSublist(Genre genre, int skip, int take) {
        List<Periodical> periodicals = new ArrayList<>();
        try {
            periodicals = perDao.getGenrePeriodicalsSublist(genre, skip, take);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return periodicals;
    }

    public List<Periodical> getPaymentPeriodicals(Payment payment) throws ServiceException {
        try {
            return perDao.getPaymentPeriodicals(payment);
        } catch (DaoException e) {
            throw new ServiceException("failed to obtain payment periodicals");
        }
    }

    public long getGenrePeriodicalCount(Genre genre) {
        long result = 0;
        try {
            result = perDao.getGenrePeriodicalCount(genre);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return result;
    }
}
