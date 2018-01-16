package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.PublishersJdbcDao;
import com.periodicals.entities.Publisher;
import com.periodicals.exceptions.DaoException;

import java.util.List;

public class PublisherService {
    private static PublisherService publisherService = new PublisherService();
    private static PublishersJdbcDao dao =
            (PublishersJdbcDao) JdbcDaoFactory.getInstance().getPublishersDao();

    private PublisherService() {

    }

    public static PublisherService getInstance() {
        return publisherService;
    }

    public Publisher getPublisherById(Integer id) {
        Publisher publisher = null;
        try {
            publisher = dao.getByKey(id);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return publisher;
    }

    public List<Publisher> getAll() {
        List<Publisher> publs = null;
        try {
            publs = dao.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return publs;
    }
}
