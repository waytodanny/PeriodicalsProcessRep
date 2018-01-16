package com.periodicals.services;

import com.periodicals.dao.entities.util.Role;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.jdbc.RolesJdbcDao;
import com.periodicals.dto.RoleDto;
import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.ServiceException;

import java.util.ArrayList;
import java.util.List;

import static com.periodicals.utils.AttributesHolder.ROLE_ADMIN;
import static com.periodicals.utils.AttributesHolder.ROLE_USER;

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

    static void fillRolesDto(List<Role> entityList, List<RoleDto> dtoList) {
        for (Role entity : entityList) {
            RoleDto dto = getDtoByEntity(entity);
            dtoList.add(dto);
        }
    }

    /*TODO checking for null*/
    static RoleDto getDtoByEntity(Role entity) {
        RoleDto dto = new RoleDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }

    public RoleDto getRoleDtoById(Byte roleId) throws ServiceException {
        try {
            Role role;
            if(USER_ROLE.getId().equals(roleId)){ /*надо ли клон?*/
                role  = USER_ROLE;
            } else {
                role = rolesDao.getById(roleId);
            }

            RoleDto dto = new RoleDto();
            dto.setId(role.getId());
            dto.setName(role.getName());

            return dto;
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

    public List<RoleDto> getAll() {
        List<Role> entities = null;
        List<RoleDto> dtos = new ArrayList<>();
        try {
            entities = rolesDao.getAll();
            fillRolesDto(entities, dtos);
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return dtos;
    }
}
