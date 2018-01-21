package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static com.periodicals.command.util.RedirectType.FORWARD;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_EDIT_USERS_PAGE;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ADMIN_USERS_PAGE;

public class EditUserCommand implements Command {
    private static final UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if(CommandUtils.isPostMethod(request)) {
            if (CommandUtils.paramClarifiedInQuery(request, "id")) {
                UUID id = UUID.fromString(request.getParameter("id"));
                String roleId = request.getParameter("role_id");

                Object[] requiredFields = {
                        roleId
                };

                if (CommandUtils.requiredFieldsNotEmpty(requiredFields)) {
                    try {
                        /*User updated = userService.getUserById(updUserId);
                        updated.setRole(roleService.getRoleById(Byte.parseByte(roleId)));
                        userService.update(updated);*/
                        request.setAttribute("resultMessage", "Successfully changed user info");
                    } catch (Exception e) {
                        request.setAttribute("resultMessage", "Failed to modify user");
                    }
                    return new CommandResult(FORWARD, ADMIN_USERS_PAGE);
                }
            }
        }

        return new CommandResult(FORWARD, ADMIN_EDIT_USERS_PAGE);
    }
}