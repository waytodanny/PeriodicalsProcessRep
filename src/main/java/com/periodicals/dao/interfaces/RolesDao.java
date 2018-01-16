package com.periodicals.dao.interfaces;

import com.periodicals.dao.entities.util.Role;
import com.periodicals.exceptions.DaoException;

public interface RolesDao extends GenericDao<Role, Byte> {
    Role getByName(String name) throws DaoException;
}
