package com.periodicals.servlet;

import com.periodicals.dao.connection.ConnectionPool;
import com.periodicals.services.LoginService;
import com.periodicals.services.entity.PeriodicalService;
import com.periodicals.services.lookup.RoleService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Initializes some components before app starts:
 * @see this.contextInitialized
 * <p>
 * Does something before it closes:
 * @see this.contextDestroyed
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance();
        LoginService.getInstance();
        RoleService.getInstance();
        PeriodicalService.getInstance();
    }

    /* TODO think of where would be better to do it*/
    /*private void createGenresList(HttpServletRequest request) {
        List<Genre> genres = GenresService.getInstance().getAllGenres();
        request.setAttribute("genres", genres);
    }

    private void createPublishersList(HttpServletRequest request) {
        List<Publisher> publishers = PublisherService.getInstance().getAllGenres();
        request.setAttribute("publishers", publishers);
    }*/

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
