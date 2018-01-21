package com.periodicals.servlet;

import com.periodicals.command.util.Command;
import com.periodicals.command.util.CommandFactory;
import com.periodicals.command.util.CommandResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.periodicals.utils.ResourceHolders.AttributesHolder.COMMAND;

//@WebServlet("/")
public class Dispatcher extends HttpServlet {

    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.dispatch(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        this.dispatch(request, response);
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response) {
        CommandFactory factory = CommandFactory.getInstance();
        String commandName = this.getCommandNameFromRequest(request);
        Command command = factory.getCommand(commandName);

        if (command != null) {
            CommandResult commandResult = command.execute(request, response);
            if (commandResult != null) {
                this.redirectFurther(request, response, commandResult);
            }
        }
    }

    private String getCommandNameFromRequest(HttpServletRequest request) {
        Object commandName = request.getAttribute(COMMAND);
        return commandName != null ? commandName.toString().toLowerCase() : "";
    }

    private void redirectFurther(HttpServletRequest request, HttpServletResponse response, CommandResult commandResult) {
        try {
            switch (commandResult.redirectType) {
                case FORWARD:
                    request.getRequestDispatcher(commandResult.pageHref).forward(request, response);
                    break;
                case REDIRECT:
                    String redirectPath = request.getServletPath() + commandResult.pageHref;
                    response.sendRedirect(redirectPath);
                    break;
            }
        } catch (ServletException | IOException e) {
            /*TODO log*/
        }
    }
}
