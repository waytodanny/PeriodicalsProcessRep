package com.periodicals.servlet;

import com.periodicals.dao.connection.ConnectionPool;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.services.LoginService;

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
        JdbcDaoFactory.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
