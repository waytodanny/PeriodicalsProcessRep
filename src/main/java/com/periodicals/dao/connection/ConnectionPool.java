package com.periodicals.dao.connection;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Connection pool that could be initialized by root or custom data source
 */
/*TODO replace data source with standard tomcat pool*/
public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class.getSimpleName());

    private static ConnectionPool instance = new ConnectionPool();
    private static DataSource dataSource;

    private ConnectionPool() {

    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    /**
     * Initializes dataSource by root application dataSource
     */
    public synchronized static void initByRootDataSource() {
        if (Objects.isNull(dataSource)) {
            dataSource = getRootDataSource();
        }
        throw new IllegalStateException("Attempt to initialize data source that is already initialized");
    }

    /**
     * Initializes dataSource by custom dataSource
     */
    public synchronized static void initByDataSource(DataSource givenDataSource) {
        if (Objects.isNull(dataSource)) {
            dataSource = givenDataSource;
        }
        throw new IllegalStateException("Attempt to initialize data source that is already initialized");
    }

    /**
     * @return root application data source object
     */
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

    public synchronized static void releaseDataSource() {
        dataSource = null;
    }

    /*TODO checking for null connection in dao*/
    Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            LOGGER.debug("Got connection from data source");
        } catch (SQLException e) {
            LOGGER.error("Failed to get connection from data source: " + e.getMessage());
        }
        return connection;
    }
}
