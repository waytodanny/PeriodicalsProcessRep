package com.periodicals.dao.connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/*TODO replace with standard tomcat pool*/
public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class.getSimpleName());

    private static ConnectionPool instance =  new ConnectionPool();
    private static DataSource dataSource;

    private ConnectionPool() {

    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public synchronized static void initByRootDataSource() {
        if(dataSource == null){
            dataSource = getRootDataSource();
        }
        throw new IllegalStateException("Attempt to initialize data source that is already initialized");
    }

    public synchronized static void initByDataSource(DataSource givenDataSource) {
        if(dataSource == null){
            dataSource = givenDataSource;
        }
        throw new IllegalStateException("Attempt to initialize data source that is already initialized");
    }

    private static DataSource getRootDataSource() {
        try {
            RootDataSourceManager manager = RootDataSourceManager.getInstance();

            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(manager.getDbDriver());
            dataSource.setUrl(manager.getJdbcUrl());
            dataSource.setUsername(manager.getDbUserLogin());
            dataSource.setPassword(manager.getDbUserPass());

            dataSource.setInitialSize(10);

            /*max number of opened connections per time*/
            dataSource.setMaxTotal(10);

            String connProps = manager.getConnectionProperties();
            if (connProps != null) {
                dataSource.setConnectionProperties(connProps);
            }
            return dataSource;
        } catch (Exception e) {
            throw new IllegalStateException();
        }
    }

    Connection getConnection() {
        Connection conn = null;
        LOGGER.debug("Trying to get connection form dataSource...");
        try {
            conn = dataSource.getConnection();
            LOGGER.debug("Got connection from dataSource");
        } catch (SQLException e) {
            LOGGER.error("Failed to get connection from dataSource: " + e.getMessage());
        }
        return conn; /*throw*/
    }

    public synchronized static void releaseDataSource(){
        dataSource = null;
    }
}
