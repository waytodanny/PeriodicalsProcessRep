package com.periodicals.services.lookup;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PublishersJdbcDao;
import com.periodicals.entities.Publisher;
import com.periodicals.exceptions.DaoException;
import com.periodicals.services.util.LookupService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PublisherService implements LookupService<Publisher, UUID> {
    private static final Logger LOGGER = Logger.getLogger(PublisherService.class.getSimpleName());

    private static final PublisherService publisherService = new PublisherService();
    private static final PublishersJdbcDao publisherDao =
            (PublishersJdbcDao) JdbcDaoFactory.getInstance().getPublishersDao();

    private PublisherService() { }

    public static PublisherService getInstance() {
        return publisherService;
    }

    public Publisher getEntityByPrimaryKey(UUID id) {
        Publisher result = null;
        try {
            result = publisherDao.getEntityByPrimaryKey(id);
            LOGGER.debug("Obtained publisher by id " + id);
        } catch (DaoException e) {
            LOGGER.debug("Failed to obtain publisher by id "+ id +" using DAO: " + e.getMessage());
        }
        return result;
    }

    public List<Publisher> getEntityCollection() {
        List<Publisher> result = new ArrayList<>();
        try {
            result = publisherDao.getEntityCollection();
            LOGGER.debug("Obtained all publishers using DAO");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain all publishers using DAO: " + e.getMessage());
        }
        return result;
    }
}
