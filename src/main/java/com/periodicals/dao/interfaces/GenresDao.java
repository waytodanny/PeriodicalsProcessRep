package com.periodicals.dao.interfaces;

import com.periodicals.dao.entities.Genre;
import com.periodicals.exceptions.DaoException;

public interface GenresDao extends GenericDao<Genre, Short> {
    Genre getGenreByName(String name) throws DaoException;
}
