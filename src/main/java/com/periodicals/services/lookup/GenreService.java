package com.periodicals.services.lookup;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.GenresJdbcDao;
import com.periodicals.entities.Genre;
import com.periodicals.exceptions.DaoException;
import com.periodicals.services.util.LookupService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GenreService implements LookupService<Genre, UUID> {
    private static final Logger LOGGER = Logger.getLogger(GenreService.class.getSimpleName());

    private static GenreService genreService = new GenreService();
    private static GenresJdbcDao genreDao = (GenresJdbcDao) JdbcDaoFactory.getInstance().getGenresDao();

    private GenreService() { }

    public static GenreService getInstance() {
        return genreService;
    }

    @Override
    public Genre getEntityByPrimaryKey(UUID id) {
        Genre result = null;
        try {
            result = genreDao.getEntityByPrimaryKey(id);
            LOGGER.debug("Obtained genre by id " + id);
        } catch (DaoException e) {
            LOGGER.debug("Failed to obtain genre by id "+ id +" using DAO: " + e.getMessage());
        }
        return result;
    }

    @Override
    public List<Genre> getEntityCollection() {
        List<Genre> result = new ArrayList<>();
        try {
            result = genreDao.getEntityCollection();
            LOGGER.debug("Obtained all genres using DAO");
        } catch (DaoException e) {
            LOGGER.error("Failed to obtain all genres using DAO: " + e.getMessage());
        }
        return result;
    }
}
