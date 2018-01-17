package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PeriodicalsJdbcDao;
import com.periodicals.dto.PeriodicalDto;
import com.periodicals.dao.entities.Genre;
import com.periodicals.dao.entities.Payment;
import com.periodicals.dao.entities.Periodical;
import com.periodicals.dao.entities.Publisher;
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
            perDao.delete(id);
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

    public List<PeriodicalDto> getPeriodicalsDtoSublist(int skip, int take) {
        List<PeriodicalDto> dtoList = new ArrayList<>();
        try {
            List<Periodical> entityList = perDao.getPeriodicalSubList(skip, take);
            fillPeriodicalsDto(entityList, dtoList);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return dtoList;
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

    public List<PeriodicalDto> getGenrePeriodicalsDtoSublist(Genre genre, int skip, int take) {
        List<PeriodicalDto> dtoList = new ArrayList<>();
        try {
            List<Periodical> entityList = perDao.getGenrePeriodicalsSublist(genre, skip, take);
            fillPeriodicalsDto(entityList, dtoList);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return dtoList;
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

    static void fillPeriodicalsDto(List<Periodical> entityList, List<PeriodicalDto> dtoList) {
        for (Periodical entity : entityList) {
            PeriodicalDto dto = getDtoByEntity(entity);
            dtoList.add(dto);
        }
    }

    /*TODO checking for null*/
    static PeriodicalDto getDtoByEntity(Periodical entity) {
        PeriodicalDto dto = new PeriodicalDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setSubscriptionCost(entity.getSubscriptionCost());
        dto.setIssuesPerYear(entity.getIssuesPerYear());
        dto.setLimited(entity.isLimited());

        Genre genre = GenresService.getInstance().getGenreById(entity.getGenre().getId());
        dto.setGenre(genre);

        Publisher publisher = PublisherService.getInstance().getPublisherById(entity.getPublisher().getId());
        dto.setPublisher(publisher);

        return dto;
    }
}
