package com.periodicals.command.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.periodicals.command.util.RedirectType.FORWARD;

/**
 * Перенаправляет на заданную страницу
 */
public class CommandResult {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final RedirectType redirectType;
    private final String pageToGo;

    public CommandResult(HttpServletRequest req, HttpServletResponse resp, RedirectType redirectType, String pageToGo) {
        this.req = req;
        this.resp = resp;
        this.redirectType = redirectType;
        this.pageToGo = pageToGo;
    }

    public void redirectFurther() {
        try {
            if (redirectType == FORWARD) {
                req.getRequestDispatcher(pageToGo).forward(req, resp);
            } else {
                resp.sendRedirect(req.getServletPath() + pageToGo);
            }
        } catch (ServletException | IOException e) {
            /*TODO log*/
        }
    }
}
