package com.periodicals.services;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.GenresJdbcDao;
import com.periodicals.dto.GenreDto;
import com.periodicals.dao.entities.Genre;
import com.periodicals.exceptions.DaoException;

import java.util.ArrayList;
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

    public List<GenreDto> getAll() {
        List<GenreDto> dtoList = new ArrayList<>();
        List<Genre> entityList = null;
        try {
            entityList = dao.getAll();
            fillDtoByEntityList(entityList, dtoList);
        } catch (DaoException e) {
            /*TODO log*/
        }
        return dtoList;
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

    private GenreDto getDtoByEntity(Genre entity) {
        GenreDto dto = new GenreDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        return dto;
    }

    private void fillDtoByEntityList(List<Genre> entityList, List<GenreDto> dtoList) {
        for (Genre entity : entityList) {
            dtoList.add(getDtoByEntity(entity));
        }
    }
}
