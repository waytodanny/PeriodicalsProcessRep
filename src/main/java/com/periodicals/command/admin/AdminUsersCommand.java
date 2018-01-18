package com.periodicals.command.admin;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandHelper;
import com.periodicals.command.util.CommandResult;
import com.periodicals.entities.Role;
import com.periodicals.entities.User;
import com.periodicals.services.RoleService;
import com.periodicals.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_EDIT_USERS_PAGE;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ERROR_PAGE;

public class AdminUsersCommand implements Command {
    private static final int RECORDS_PER_PAGE = 5;
    private UserService userService = UserService.getInstance();
    private RoleService roleService = RoleService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) {
        String delUserId = req.getParameter("remId");
        String updUserId = req.getParameter("updId");

        if (CommandHelper.paramIsNotEmpty(delUserId)) {
//            userService.delete(delUserId);

        } else if (CommandHelper.paramIsNotEmpty(updUserId)) {
            try {
                User upToEdit = userService.getUserById(updUserId);

                String updRoleIdParam = req.getParameter("updateRoleId");
                Byte updRoleId = Byte.parseByte(updRoleIdParam);
//                if (!Objects.equals(upToEdit.getRoleId(), updRoleId)) {
//                    upToEdit.setRoleId(updRoleId);
//                }

                userService.update(upToEdit);
            } catch (NumberFormatException e) {
                /*TODO msg, log*/
                return new CommandResult(req, resp, FORWARD, ERROR_PAGE);
            } catch (Exception e) {
                /*TODO msg, log*/
                return new CommandResult(req, resp, FORWARD, ERROR_PAGE);
            }
        }

        createRolesList(req);

        int page = 1;
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));

//        int entriesCount = userService.getUsersCount();
//        int pagesCount = (int) Math.ceil(entriesCount * 1.0 / RECORDS_PER_PAGE);
//
//        int skip = (page - 1) * RECORDS_PER_PAGE;
//        List<User> users = userService.getUsersSublist(skip, RECORDS_PER_PAGE);
//
//
//        req.setAttribute("users", users);
//        req.setAttribute("pagesCount", pagesCount);
//        req.setAttribute("currentPage", page);

        return new CommandResult(req, resp, FORWARD, ADMIN_EDIT_USERS_PAGE + "?page=" + page);
    }

    private void createRolesList(HttpServletRequest req) {
        List<Role> roles = roleService.getAll();
        req.setAttribute("roles", roles);
    }
}
