package com.periodicals.dao.connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.sql.Connection;
import java.sql.SQLException;

/*TODO replace with standard tomcat pool*/
public class ConnectionPool {
    static Logger log = Logger.getLogger(ConnectionPool.class.getSimpleName());

    private static ConnectionPool instance = new ConnectionPool();
    private static BasicDataSource dataSource;

    static {
        new DOMConfigurator().doConfigure("src\\main\\resources\\log4j.xml", LogManager.getLoggerRepository());
    }

    private ConnectionPool() {
        initDataSource();
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    private static void initDataSource() {
        try {
            DataSourceManager manager = DataSourceManager.getInstance();

            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(manager.getDbDriver());
            dataSource.setUrl(manager.getJdbcUrl());
            dataSource.setUsername(manager.getDbUserLogin());
            dataSource.setPassword(manager.getDbUserPass());

            dataSource.setInitialSize(10);

            /*max number of opened connections same time*/
            dataSource.setMaxTotal(10);

            String connProps = manager.getConnectionProperties();
            if (connProps != null) {
                dataSource.setConnectionProperties(connProps);
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage()); /*log*/
        }
    }

    Connection getConnection() {
        Connection conn = null;
        log.debug("Trying to get connection form dataSource...");
        try {
            conn = dataSource.getConnection();
            log.debug("Got connection from dataSource");
        } catch (SQLException e) {
            log.error("Failed to get connection from dataSource: " + e.getMessage());
        }
        return conn; /*throw*/
    }
}
