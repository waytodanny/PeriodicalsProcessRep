package com.periodicals.services.lookup;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.RolesJdbcDao;
import com.periodicals.entities.Role;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;
import com.periodicals.services.util.LookupService;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.ResourceHolders.AttributesHolder.ROLE_ADMIN_ID;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.ROLE_USER_ID;

public class RoleService implements LookupService<Role, UUID> {
    private static final Logger LOGGER = Logger.getLogger(RoleService.class.getSimpleName());

    public static final UUID USER_ROLE_ID = UUID.fromString(ROLE_USER_ID);
    public static final UUID ADMIN_ROLE_ID = UUID.fromString(ROLE_ADMIN_ID);

    private static RoleService roleService = new RoleService();
    private static RolesJdbcDao rolesDao =
            (RolesJdbcDao) JdbcDaoFactory.getInstance().getRolesDao();

    private RoleService() {

    }

    public static RoleService getInstance() {
        return roleService;
    }

    @Override
    public Role getEntityByPrimaryKey(UUID key) throws ServiceException {
        try {
            return rolesDao.getEntityByPrimaryKey(key);
        } catch (DaoException e) {
            LOGGER.debug("Failed to get role by PK: " + key + ": " + e.getMessage());
            throw new ServiceException("Failed to get role by PK: " + key + ": " + e.getMessage());
        }
    }

    @Override
    public List<Role> getEntityCollection() {
        List<Role> roles = new ArrayList<>();
        try {
            roles = rolesDao.getEntityCollection();
        } catch (DaoException e) {
            LOGGER.debug("Failed to obtain roles: " + e.getMessage());
        }
        return roles;
    }
}
