package com.periodicals.command.admin;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandResult;
import com.periodicals.command.util.CommandUtils;
import com.periodicals.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class DeleteUserCommand implements Command {
    private static final UserService userService = UserService.getInstance();

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        if(CommandUtils.isPostMethod(request)) {
            if (CommandUtils.paramClarifiedInQuery(request, "id")) {
                UUID id = UUID.fromString(request.getParameter("id"));
                String removableId = request.getParameter("id");
                try {
                    userService.deleteUserById(removableId);
                    request.setAttribute("resultMessage", "Successfully deleted periodical");
                } catch (Exception e) {
                    request.setAttribute("resultMessage", "Failed to deleteUserById periodical");
                }
            }
        }
        return null;
    }
}

