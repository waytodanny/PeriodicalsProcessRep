package com.periodicals.dao.connection;

import java.io.IOException;
import java.util.Properties;

public class DataSourceManager {
    private static DataSourceManager instance = new DataSourceManager();

    private String jdbcUrl;
    private String dbUserLogin;
    private String dbUserPass;
    private String dbDriver;
    private String connectionProperties;

    private DataSourceManager() {
        Properties prop = new Properties();
        try {
            prop.load(DataSourceManager.class.getClassLoader().getResourceAsStream("application.properties"));
            this.dbDriver = prop.getProperty("dbDriver");
            this.jdbcUrl = prop.getProperty("jdbcUrl");
            this.dbUserLogin = prop.getProperty("dbUserLogin");
            this.dbUserPass = prop.getProperty("dbUserPassword");
            this.connectionProperties = prop.getProperty("connProperties");
        } catch (IOException e) {

        }
    }

    public static DataSourceManager getInstance() {
        return instance;
    }

    String getJdbcUrl() {
        return jdbcUrl;
    }

    String getDbUserLogin() {
        return dbUserLogin;
    }

    String getDbUserPass() {
        return dbUserPass;
    }

    String getDbDriver() {
        return dbDriver;
    }

    String getConnectionProperties() {
        return connectionProperties;
    }
}
