package com.periodicals.services.entities;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PeriodicalsJdbcDao;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Publisher;
import com.periodicals.entities.Genre;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.services.lookups.GenreService;
import com.periodicals.services.interfaces.LookupService;
import com.periodicals.services.interfaces.PageableCollectionService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Daniel Volnitsky
 * <p>
 * Service responsible for processing CRUD and specified seeking operations with Periodicals
 * @see Periodical
 */
public class PeriodicalService implements PageableCollectionService<Periodical>, LookupService<Periodical, UUID> {
    private static final Logger LOGGER = Logger.getLogger(PeriodicalService.class.getSimpleName());

    private static final PeriodicalService periodicalService = new PeriodicalService();
    private static final GenreService genreService = GenreService.getInstance();
    private static final PublisherService publisherService = PublisherService.getInstance();
    private static final PaymentService paymentService = PaymentService.getInstance();

    private static final PeriodicalsJdbcDao periodicalsDao =
            (PeriodicalsJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalsDao();

    public static PeriodicalService getInstance() {
        return periodicalService;
    }


    @Override
    public Periodical getEntityByPrimaryKey(UUID id)  {
        Periodical result = null;
        try {
            result = periodicalsDao.getEntityByPrimaryKey(id);
            LOGGER.debug("Obtained periodical with id " + id);
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain periodical with id "+ id + " due to: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Periodical> getEntityCollection() {
        List<Periodical> entities = new ArrayList<>();
        try {
            entities = periodicalsDao.getEntityCollection();
            LOGGER.debug("Obtained all periodicals");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain all periodicals: " + e.getMessage());
        }
        return entities;
    }

    /**
     * Constructs and inserts Periodical object by incoming params
     */
    public void createEntity(String name, String description, BigDecimal subscriptionCost, boolean isLimited,
                          short issuesPerYear, UUID genreId, UUID publisherId) throws ServiceException {
        try {
            Periodical added = new Periodical();
            UUID id = UUID.randomUUID();

            added.setId(id);
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
            periodicalsDao.createEntity(added);
            LOGGER.debug("Periodical with id " + id + " has been successfully created");
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * updates Periodical object by incoming params
     */
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
                periodicalsDao.updateEntity(updated);
                LOGGER.debug("Periodical with id " + id + " has been successfully updated");
            } else {
                throw new NullPointerException("Periodical with id " + id + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    /**
     * deletes Periodical object by incoming params
     */
    public void deleteEntity(UUID id) throws ServiceException {
        try {
            Periodical deleted = this.getEntityByPrimaryKey(id);
            if(Objects.isNull(deleted)) {
                throw new NullPointerException("Periodical with id " + id + " doesn't exist");
            }
            periodicalsDao.deleteEntity(deleted);
            LOGGER.debug("Periodical with id " + id + " has been successfully deleted");
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Periodical> getEntitiesListBounded(int skip, int limit) {
        List<Periodical> entities = new ArrayList<>();
        try {
            entities = periodicalsDao.getEntitiesListBounded(skip, limit);
            LOGGER.debug("Obtained periodicals bounded list");
        } catch (DaoException e) {
            LOGGER.error("Failed to get periodicals bounded list: " + e.getMessage());
        }
        return entities;
    }

    @Override
    public int getEntitiesCount() {
        int result = 0;
        try {
            result = periodicalsDao.getEntitiesCount();
            LOGGER.debug("Obtained all periodicals count");
        } catch (DaoException e) {
            LOGGER.error("Failed to get all periodicals count: " + e.getMessage());
        }
        return result;
    }

    /**
     * @return  Periodical limited list of specified genre
     * @param genreId id of genre which periodicals are to be obtained
     */
    public List<Periodical> getPeriodicalsByGenreListBounded(int skip, int limit, UUID genreId) {
        List<Periodical> entities = new ArrayList<>();
        try {
            Genre genre = genreService.getEntityByPrimaryKey(genreId);
            if(Objects.nonNull(genre)) {
                entities = periodicalsDao.getPeriodicalsByGenreListBounded(skip, limit, genre);
                LOGGER.debug("Obtained periodicals bounded list with genreId " + genreId);
            } else {
                throw new NullPointerException("Genre with id " + genreId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return entities;
    }

    /**
     * @return  Periodicals of specified genre count
     * @param genreId id of genre which periodicals count is to be obtained
     */
    public int getPeriodicalsByGenreCount(UUID genreId) {
        int result = 0;
        try {
            Genre genre = genreService.getEntityByPrimaryKey(genreId);
            if(Objects.nonNull(genre)) {
                result = periodicalsDao.getPeriodicalsByGenreCount(genre);
                LOGGER.debug("Obtained periodicals list count with genreId " + genreId);
            } else {
                throw new NullPointerException("Genre with id " + genreId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    /**
     * @return  Periodicals of specified genre count
     * @param paymentId id of payment which periodicals limited list is to be obtained
     */
    public List<Periodical> getPeriodicalsByPaymentListBounded(int skip, int limit, UUID paymentId) {
        List<Periodical> entities = new ArrayList<>();
        try {
            Payment payment = paymentService.getEntityByPrimaryKey(paymentId);
            if(Objects.nonNull(payment)) {
                entities = periodicalsDao.getPeriodicalsByPaymentList(payment);
                LOGGER.debug("Obtained periodicals list with paymentId " + paymentId);
            } else {
                throw new NullPointerException("Payment with id " + paymentId + " doesn't exist");
            }
        } catch (DaoException | NullPointerException e) {
            LOGGER.error(e.getMessage());
        }
        return entities;
    }
}