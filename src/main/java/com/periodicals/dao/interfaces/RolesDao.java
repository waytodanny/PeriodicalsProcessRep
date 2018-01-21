package com.periodicals.dao.interfaces;

import com.periodicals.entities.Role;
import com.periodicals.exceptions.DaoException;

public interface RolesDao extends GenericDao<Role, String> {
    Role getByName(String name) throws DaoException;
}
