package com.periodicals.services;

import com.periodicals.entities.Role;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.RolesJdbcDao;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;

import java.util.List;

import static com.periodicals.utils.ResourceHolders.AttributesHolder.ROLE_ADMIN;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.ROLE_USER;

public class RoleService {
    private static RoleService roleService = new RoleService();
    private static RolesJdbcDao rolesDao =
            (RolesJdbcDao) JdbcDaoFactory.getInstance().getRolesDao();

    public static final Role USER_ROLE = roleService.getRole(ROLE_USER);
    public static final Role ADMIN_ROLE = roleService.getRole(ROLE_ADMIN);

    private RoleService() {

    }

    public static RoleService getInstance() {
        return roleService;
    }

    public Role getRoleById(Byte roleId) throws ServiceException {
        try {
            Role role;
            if (USER_ROLE.getId().equals(roleId)) { /*надо ли клон?*/
                role = USER_ROLE;
            } else {
                role = rolesDao.getById(roleId);
            }
            return role;
        } catch (DaoException e) {
            throw new ServiceException("failed to obtain user role");
        }
    }

    public Role getRole(String roleName) {
        Role role = null;
        try {
            role = rolesDao.getByName(roleName);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return role;
    }

    public List<Role> getAll() {
        List<Role> roles = null;
        try {
            roles = rolesDao.getAll();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return roles;
    }
}
