//package dbTestHelpers;
//
//import org.h2.jdbcx.JdbcConnectionPool;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class InMemoryDbManager {
//    public static final JdbcConnectionPool CONN_POOL =
//            JdbcConnectionPool.create("jdbc:h2:mem:test", "sa", "sa");
//
//    private static final String CREATE_DB_SCRIPT_PATH =
//            "src\\main\\resources\\createTestDB.sql";
//    private static final String INSERT_DEFAULTS_SCRIPT_PATH =
//            "src\\main\\resources\\mySQL_InsertDefaults_script.sql";
//
//    public InMemoryDbManager() throws SQLException, IllegalArgumentException {
//        initializeDB();
//    }
//
//    private void initializeDB() throws SQLException {
//        executeSQLScriptsFromFile(CREATE_DB_SCRIPT_PATH);
//    }
//
//    public void insertDefaultData() throws SQLException, IllegalArgumentException {
//        executeSQLScriptsFromFile(INSERT_DEFAULTS_SCRIPT_PATH);
//    }
//
//    public void truncateTable(String tableName) throws SQLException {
//        try (Connection conn = getConnection();
//             Statement statement = conn.createStatement()) {
//
//            String sqlTruncate = "DELETE FROM " + tableName + ";";
//            statement.executeUpdate(sqlTruncate);
//        } catch (SQLException e) {
//            throw new SQLException("FAILED TO TRUNCATE TABLE");
//        }
//    }
//
////    public void dropDB(){
////        try (Connection conn = getConnectionWrapper();
////             Statement statement = conn.createStatement()) {
////
////            String sqlDrop = "";
////            statement.executeUpdate(sqlDrop);
////        } catch (SQLException e) {
////            throw new SQLException("FAILED TO TRUNCATE TABLE");
////        }
////    }
//
//    private void executeSQLScriptsFromFile(String filePath) throws SQLException, IllegalArgumentException{
//        File file = new File(filePath);
//        try (Connection conn = CONN_POOL.getConnection();
//             Statement statement = conn.createStatement()) {
//
//            String script = new String(Files.readAllBytes(file.toPath()));
//            script = script.replaceAll("[\\s]+", " ");
//            String[] queries = script.split(";");
//            try {
//                for (String query : queries) {
//                    if (!query.trim().isEmpty()) {
//                        statement.parse(query);
//                    }
//                }
//            } catch (SQLException e) {
//                throw new SQLException(e);
//            }
//        } catch (IOException e) {
//            throw new IllegalArgumentException(e);
//        } catch (SQLException e){
//            throw e;
//        }
//    }
//
//    public Connection getConnection() {
//        Connection conn = null;
////        log.info("Trying to get connection form dataSource");
//        try {
//            conn = CONN_POOL.getConnection();
////            log.info("Got connection from dataSource");
//        } catch (SQLException e) {
////            log.error("Failed to get connection from dataSource: " + e.getMessage());
//        }
//        return conn; /*throw*/
//    }
//}
