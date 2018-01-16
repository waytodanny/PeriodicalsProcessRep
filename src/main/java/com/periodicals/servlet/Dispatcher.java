package com.periodicals.servlet;

import com.periodicals.command.Command;
import com.periodicals.command.util.CommandFactory;
import com.periodicals.command.util.CommandResult;
import com.periodicals.services.PeriodicalService;
import com.periodicals.utils.PagesHolder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.periodicals.utils.AttributesHolder.COMMAND;

//@WebServlet("/")
public class Dispatcher extends HttpServlet {
    @Override
    public void init() throws ServletException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        dispatch(req, resp);
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp) {
        CommandFactory factory = CommandFactory.getInstance();

        String path = getCorrectedPath(req);
        Command command = factory.getCommand(path);

        CommandResult comResult = command.execute(req, resp);
        comResult.redirectFurther();
    }

    private String getCorrectedPath(HttpServletRequest req) {
        return ((String) req.getAttribute(COMMAND)).toLowerCase();
    }
}
