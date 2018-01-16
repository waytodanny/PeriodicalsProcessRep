package com.periodicals.dao.interfaces;

import com.periodicals.dao.interfaces.GenericDao;
import com.periodicals.entities.Role;
import com.periodicals.exceptions.DaoException;

public interface RolesDao extends GenericDao<Role, Byte> {
    Role getByName(String name) throws DaoException;
}
