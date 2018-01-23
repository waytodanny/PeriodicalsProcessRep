package com.periodicals.dao.connection;

import java.io.IOException;
import java.util.Properties;

public class RootDataSourceManager {
    private static RootDataSourceManager instance = new RootDataSourceManager();

    private String jdbcUrl;
    private String dbUserLogin;
    private String dbUserPass;
    private String dbDriver;
    private String connectionProperties;

    private RootDataSourceManager() {
        Properties prop = new Properties();
        try {
            prop.load(RootDataSourceManager.class.getClassLoader().getResourceAsStream("application.properties"));
            this.dbDriver = prop.getProperty("dbDriver");
            this.jdbcUrl = prop.getProperty("jdbcUrl");
            this.dbUserLogin = prop.getProperty("dbUserLogin");
            this.dbUserPass = prop.getProperty("dbUserPassword");
            this.connectionProperties = prop.getProperty("connProperties");
        } catch (IOException e) {

        }
    }

    public static RootDataSourceManager getInstance() {
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
