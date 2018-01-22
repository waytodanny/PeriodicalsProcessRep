package com.periodicals.services.entity;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PeriodicalsJdbcDao;
import com.periodicals.entities.Genre;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.Publisher;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.services.lookup.GenreService;
import com.periodicals.services.lookup.PublisherService;
import com.periodicals.services.util.PageableCollectionService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PeriodicalService implements PageableCollectionService<Periodical> {
    private static final Logger LOGGER = Logger.getLogger(PeriodicalService.class.getSimpleName());

    private static final PeriodicalService periodicalService = new PeriodicalService();
    private static final GenreService genreService = GenreService.getInstance();
    private static final PublisherService publisherService = PublisherService.getInstance();
    private static final PaymentService paymentService = PaymentService.getInstance();

    private static final PeriodicalsJdbcDao periodicalDao =
            (PeriodicalsJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalsDao();

    private PeriodicalService() { }

    public static PeriodicalService getInstance() {
        return periodicalService;
    }

    public List<Periodical> getEntityCollection() {
        List<Periodical> result = new ArrayList<>();
        try {
            result = periodicalDao.getEntityCollection();
            LOGGER.debug("Obtained all periodicals using DAO");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain all periodicals using DAO: " + e.getMessage());
        }
        return result;
    }

    public Periodical getEntityByPrimaryKey(UUID id) {
        Periodical result = null;
        try {
            result = periodicalDao.getEntityByPrimaryKey(id);
            LOGGER.debug("Obtained periodical by id " + id);
        } catch (DaoException e) {
            LOGGER.debug("Failed to obtain periodical by id "+ id +" using DAO: " + e.getMessage());
        }
        return result;
    }

    public void createEntity(String name, String description, BigDecimal subscriptionCost, boolean isLimited,
                          short issuesPerYear, UUID genreId, UUID publisherId) throws ServiceException {
        try {
            Periodical added = new Periodical();
            added.setName(name);
            added.setDescription(description);
            added.setSubscriptionCost(subscriptionCost);
            added.setLimited(isLimited);
            added.setIssuesPerYear(issuesPerYear);

            Genre addedGenre = genreService.getEntityByPrimaryKey(genreId);
            if(Objects.nonNull(addedGenre)) {
                added.setGenre(addedGenre);
            } else {
                throw new NullPointerException("Genre with id " + genreId + " doesn't exist");
            }

            Publisher addedPublisher = publisherService.getEntityByPrimaryKey(publisherId);
            if(Objects.nonNull(addedPublisher)) {
                added.setPublisher(addedPublisher);
            } else {
                throw new NullPointerException("Publisher with id " + publisherId + " doesn't exist");
            }
            periodicalDao.createEntity(added);
            LOGGER.debug("Successful periodical adding");
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    public void updateEntity(UUID id, String name, String description, BigDecimal subscriptionCost, boolean isLimited,
                             short issuesPerYear, UUID genreId, UUID publisherId) throws ServiceException {
        try {
            Periodical updated = this.getEntityByPrimaryKey(id);
            if(Objects.nonNull(updated)) {
                updated.setName(name);
                updated.setDescription(description);
                updated.setSubscriptionCost(subscriptionCost);
                updated.setLimited(isLimited);
                updated.setIssuesPerYear(issuesPerYear);

                Genre updatedGenre = genreService.getEntityByPrimaryKey(genreId);
                if(Objects.nonNull(updatedGenre)) {
                    updated.setGenre(updatedGenre);
                } else {
                    throw new NullPointerException("Genre with id " + genreId + " doesn't exist");
                }

                Publisher updatedPublisher = publisherService.getEntityByPrimaryKey(publisherId);
                if(Objects.nonNull(updatedPublisher)) {
                    updated.setPublisher(updatedPublisher);
                } else {
                    throw new NullPointerException("Publisher with id " + publisherId + " doesn't exist");
                }

                periodicalDao.updateEntity(updated);
                LOGGER.debug("Successful update of periodical with id " + id);
            } else {
                throw new NullPointerException("Periodical with id " + id + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    public void deleteEntity(UUID id) throws ServiceException {
        try {
            Periodical deleted = this.getEntityByPrimaryKey(id);
            if(Objects.nonNull(deleted)) {
                periodicalDao.deleteEntity(deleted.getId());
                LOGGER.debug("Successful deletion of periodical with id " + id);
            } else {
                throw new NullPointerException("Periodical with id " + id + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Periodical> getEntitiesListBounded(int skip, int limit) {
        List<Periodical> entities = new ArrayList<>();
        try {
            entities = periodicalDao.getPeriodicalListBounded(skip, limit);
            LOGGER.debug("Obtained periodicals bounded list");
        } catch (DaoException e) {
            LOGGER.error("Failed to get periodicals bounded list using DAO: " + e.getMessage());
        }
        return entities;
    }

    @Override
    public int getEntitiesCount() {
        int result = 0;
        try {
            result = periodicalDao.getEntitiesCount();
            LOGGER.debug("Obtained all periodicals count");
        } catch (DaoException e) {
            LOGGER.error("Failed to get periodicals count using DAO: " + e.getMessage());
        }
        return result;
    }

    public List<Periodical> getPeriodicalsByGenreListBounded(UUID genreId, int skip, int limit) {
        List<Periodical> entities = new ArrayList<>();
        try {
            Genre genre = genreService.getEntityByPrimaryKey(genreId);
            if(Objects.nonNull(genre)) {
                entities = periodicalDao.getGenrePeriodicalsLimited(genre, skip, limit);
                LOGGER.debug("Obtained genre periodicals bounded list");
            } else {
                throw new NullPointerException("Genre with id " + genreId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return entities;
    }

    public int getPeriodicalsByGenreCount(UUID genreId) {
        int result = 0;
        try {
            Genre genre = genreService.getEntityByPrimaryKey(genreId);
            if(Objects.nonNull(genre)) {
                result = periodicalDao.getGenrePeriodicalsCount(genre);
                LOGGER.debug("Obtained genre periodicals count");
            } else {
                throw new NullPointerException("Genre with id " + genreId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public List<Periodical> getPeriodicalsByPaymentList(UUID paymentId) throws ServiceException {
        List<Periodical> periodicals = new ArrayList<>();
        try {
            Payment payment = paymentService.getEntityByPrimaryKey(paymentId);
            if(Objects.nonNull(payment)) {
                periodicals = periodicalDao.getPaymentPeriodicals(payment);
                LOGGER.debug("Obtained payment periodicals");
            } else {
                throw new NullPointerException("Payment with id " + paymentId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return periodicals;
    }
}