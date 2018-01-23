//package com.periodicals.command.admin;
//
//import com.periodicals.command.util.Command;
//import com.periodicals.command.util.CommandResult;
//import com.periodicals.command.util.CommandUtils;
//import com.periodicals.services.entity.PeriodicalIssueService;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class DeleteIssueCommand implements Command {
//    private static final PeriodicalIssueService periodicalIssueService = PeriodicalIssueService.getInstance();
//
//    @Override
//    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
//        if (CommandUtils.isPostMethod(request)) {
//            if (CommandUtils.paramClarifiedInQuery(request, "id")) {
//                String deleteId = request.getParameter("id");
//                try {
//                    periodicalIssueService.deleteById(deleteId);
////                    request.setAttribute("resultMessage", "Successfully deleted issue");
//                } catch (Exception e) {
////                    request.setAttribute("resultMessage", "Failed to deleteUserById issue");
//                }
//            }
//        }
//        return null;
//    }
//}
