package com.periodicals.dao.interfaces;

import com.periodicals.entities.Role;
import com.periodicals.exceptions.DaoException;

import java.util.UUID;

public interface RolesDao extends GenericDao<Role, UUID> {
    Role getByName(String name) throws DaoException;
}
