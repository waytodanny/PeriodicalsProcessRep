//package com.periodicals.command.admin;
//
//import com.periodicals.command.util.Command;
//import com.periodicals.command.util.CommandResult;
//import com.periodicals.command.util.CommandUtils;
//import com.periodicals.services.PeriodicalService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class DeletePeriodicalCommand implements Command {
//    private static final PeriodicalService periodicalService = PeriodicalService.getInstance();
//
//    @Override
//    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
//        if (CommandUtils.isPostMethod(request)) {
//            if (CommandUtils.paramClarifiedInQuery(request, "id")) {
//                String removableId = request.getParameter("id");
//                try {
//                    periodicalService.deletePeriodicalById(removableId);
//                    request.setAttribute("resultMessage", "Successfully deleted periodical");
//                } catch (Exception e) {
//                    request.setAttribute("resultMessage", "Failed to deleteUserById periodical");
//                }
//            }
//        }
//        return null;
//    }
//}
