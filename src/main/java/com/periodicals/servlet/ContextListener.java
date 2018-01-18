package com.periodicals.servlet;

import com.periodicals.dao.connection.ConnectionPool;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.services.GenresService;
import com.periodicals.services.LoginService;
import com.periodicals.services.PeriodicalService;
import com.periodicals.services.RoleService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Initializes some components before app starts and do something before it ends
 *
 * @see ServletContextListener
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance();
        LoginService.getInstance();
        RoleService.getInstance();
        PeriodicalService.getInstance();
        GenresService.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
