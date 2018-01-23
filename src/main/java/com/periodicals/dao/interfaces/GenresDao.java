package com.periodicals.dao.interfaces;

import com.periodicals.entities.Genre;
import com.periodicals.exceptions.DaoException;

import java.util.UUID;

public interface GenresDao extends GenericDao<Genre, UUID> {
    Genre getGenreByName(String name) throws DaoException;
}
