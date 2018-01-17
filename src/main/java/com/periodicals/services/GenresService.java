package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.GenresJdbcDao;
import com.periodicals.entities.Genre;
import com.periodicals.exceptions.DaoException;

import java.util.List;

public class GenresService {
    private static GenresService genresService = new GenresService();
    private static GenresJdbcDao dao =
            (GenresJdbcDao) JdbcDaoFactory.getInstance().getGenresDao();

    private GenresService() {

    }

    public static GenresService getInstance() {
        return genresService;
    }

    public Genre getGenreById(Short genreId) {
        Genre genre = null;
        try {
            genre = dao.getById(genreId);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return genre;
    }

    public List<Genre> getAll() {
        List<Genre> entityList = null;
        try {
            entityList = dao.getAll();
        } catch (DaoException e) {
            /*TODO log*/
        }
        return entityList;
    }

    public Genre getGenre(String genreName) {
        Genre entity = null;
        try {
            entity = dao.getGenreByName(genreName);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return entity;
    }
}
