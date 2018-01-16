//package com.periodicals.dao.jdbc;
//
//import com.periodicals.dao.factories.JdbcDaoFactory;
//import com.periodicals.dao.entities.Publisher;
//import com.periodicals.exceptions.DaoException;
//import dbTestHelpers.InMemoryDbManager;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.apache.log4j.xml.DOMConfigurator;
//import org.junit.jupiter.api.*;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PublishersJdbcDAOTest {
//
//    static final String PUBLISHERS_TABLE_NAME = "publishers";
//    static InMemoryDbManager inmManager;
//    static PublishersJdbcDao publsDAO;
//    static Publisher testPublisher1, testPublisher2, nonExistingPublisher, samePublisherFromDB;
//    static Connection conn;
//
//    static Logger log = Logger.getLogger(PublishersJdbcDAOTest.class.getSimpleName());
//
//    static {
//        new DOMConfigurator().doConfigure("src\\main\\resources\\log4j.xml", LogManager.getLoggerRepository());
//    }
//
//    @BeforeAll
//    static void BeforeAll() {
//        try {
//            inmManager = new InMemoryDbManager();
//
//            nonExistingPublisher = new Publisher("non existing name");
//            nonExistingPublisher.setId(112);
//
//            testPublisher1 = new Publisher("Marvel");
//            testPublisher2 = new Publisher("Dc");
//        } catch (SQLException | IllegalArgumentException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @AfterAll
//    static void AfterAll() {
//        publsDAO = null;
//        inmManager = null;
//        testPublisher1 = null;
//        testPublisher2 = null;
//        nonExistingPublisher = null;
//        samePublisherFromDB = null;
//    }
//
//    @BeforeEach
//    void setUp() {
//        conn = inmManager.getConnection();
//        publsDAO = (PublishersJdbcDao) JdbcDaoFactory.getInstance().getPublishersDao();
//    }
//
//    @AfterEach
//    void tearDown() {
//        testPublisher1.setId(null);
//        testPublisher2.setId(null);
//        samePublisherFromDB = null;
//        try {
//            conn.close();
//            inmManager.truncateTable(PUBLISHERS_TABLE_NAME);
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void addPublisher() {
//        try {
//            List<Publisher> publsBefore = publsDAO.getAll();
//            assertTrue(publsBefore.size() == 0);
//
//            publsDAO.add(testPublisher1);
//            List<Publisher> publsAfter = publsDAO.getAll();
//
//            assertTrue(testPublisher1.getId() != 0);
//            samePublisherFromDB = publsDAO.getById(testPublisher1.getId());
//
//            assertNotNull(samePublisherFromDB);
//            assertEquals(testPublisher1, samePublisherFromDB);
//            assertEquals(1, publsAfter.size());
//            assertEquals(testPublisher1, samePublisherFromDB);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void addNullablePublisher() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            publsDAO.add(null);
//        });
//    }
//
//    @Test
//    void addPublisherWithNonNullKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            testPublisher1.setId(1);
//            publsDAO.add(testPublisher1);
//        });
//    }
//
//    @Test
//    void addPublisherWithSameName() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            publsDAO.add(testPublisher1);
//            publsDAO.add(new Publisher(testPublisher1.getName()));
//        });
//    }
//
//    @Test
//    void addSamePublisherTwice() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            publsDAO.add(testPublisher1);
//            publsDAO.add(testPublisher1);
//        });
//    }
//
//    @Test
//    void getByValidPK() {
//        try {
//            publsDAO.add(testPublisher1);
//            assertNotNull(testPublisher1.getId());
//
//            samePublisherFromDB = publsDAO.getById(testPublisher1.getId());
//
//            assertNotNull(samePublisherFromDB);
//            assertEquals(testPublisher1, samePublisherFromDB);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getByInvalidPK() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            publsDAO.getById(11);
//        });
//    }
//
//    @Test
//    void getByNullableKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            publsDAO.getById(null);
//        });
//    }
//
//    @Test
//    void updateExistingPublisher() {
//        try {
//            publsDAO.add(testPublisher1);
//            assertTrue(testPublisher1.getId() != 0);
//
//            samePublisherFromDB = publsDAO.getById(testPublisher1.getId());
//            assertEquals(testPublisher1, samePublisherFromDB);
//
//            String newName = "Marvel v.2";
//            samePublisherFromDB.setName(newName);
//            publsDAO.update(samePublisherFromDB);
//
//            samePublisherFromDB = publsDAO.getById(testPublisher1.getId());
//            assertNotEquals(samePublisherFromDB, testPublisher1);
//            assertEquals(newName, samePublisherFromDB.getName());
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void updateNonExistingPublisher() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            publsDAO.update(nonExistingPublisher);
//        });
//    }
//
//    @Test
//    void updateNullablePublisher() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            publsDAO.update(null);
//        });
//    }
//
//    @Test
//    void deleteExistingPublisher() {
//        try {
//            publsDAO.add(testPublisher1);
//            assertTrue(testPublisher1.getId() != 0);
//
//            int expectedTableSize = 1;
//            int resultTableSize = publsDAO.getAll().size();
//            assertEquals(expectedTableSize, resultTableSize);
//
//            samePublisherFromDB = publsDAO.getById(testPublisher1.getId());
//            assertEquals(testPublisher1, samePublisherFromDB);
//
//            publsDAO.delete(samePublisherFromDB.getId());
//
//            expectedTableSize = 0;
//            resultTableSize = publsDAO.getAll().size();
//            assertEquals(expectedTableSize, resultTableSize);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void deleteNonExistingPublisher() {
//        assertThrows(DaoException.class, () -> {
//            assertFalse(publsDAO.delete(nonExistingPublisher.getId()));
//        });
//    }
//
//    @Test
//    void deleteNullablePublisher() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            publsDAO.delete(null);
//        });
//    }
//
//    @Test
//    void deleteNotIdentifiedPublisher() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            publsDAO.delete(testPublisher2.getId());
//        });
//    }
//}