//package com.periodicals.dao.jdbc;
//
//import com.periodicals.exceptions.DaoException;
//import com.periodicals.dao.factories.JdbcDaoFactory;
//import com.periodicals.entities.PeriodicalIssue;
//import dbTestHelpers.InMemoryDbManager;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.apache.log4j.xml.DOMConfigurator;
//import org.junit.jupiter.api.*;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PeriodicalIssuesJdbcDAOTest {
//    private static InMemoryDbManager inmManager;
//    private static PeriodicalIssuesJdbcDao issuesDao;
//    private static PeriodicalIssue
//            testIssue, nonExistingIssue, sameIssueFromDB, insertedIssue;
//
//    private static Connection conn;
//
//    private static Logger log = Logger.getLogger(PeriodicalIssuesJdbcDAOTest.class.getSimpleName());
//
//    static {
//        new DOMConfigurator().doConfigure("src\\main\\resources\\log4j.xml", LogManager.getLoggerRepository());
//    }
//
//    @BeforeAll
//    static void BeforeAll() {
//        try {
//            inmManager = new InMemoryDbManager();
//            inmManager.insertDefaultData();
//
//            testIssue = new PeriodicalIssue("spider-man vol.3", 1);
//            nonExistingIssue = new PeriodicalIssue("non existing name", 2);
//            nonExistingIssue.setId(112);
//        } catch (SQLException | IllegalArgumentException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @AfterAll
//    static void AfterAll() {
//        inmManager = null;
//        issuesDao = null;
//        testIssue = null;
//        nonExistingIssue = null;
//        insertedIssue = null;
//    }
//
//    @BeforeEach
//    void setUp() {
//        try {
//            conn = inmManager.getConnection();
//            issuesDao = (PeriodicalIssuesJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalIssuesDao();
//            insertedIssue = issuesDao.getByKey(1);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @AfterEach
//    void tearDown() {
//        testIssue.setId(null);
//        sameIssueFromDB = null;
//        try {
//            conn.close();
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void addPeriodicalIssue() {
//        try {
//            issuesDao.add(testIssue);
//            assertTrue(testIssue.getId() != 0);
//
//            sameIssueFromDB = issuesDao.getByKey(testIssue.getId());
//
//            assertNotNull(sameIssueFromDB);
//
//            testIssue.setPublishDate(sameIssueFromDB.getPublishDate());
//            assertEquals(sameIssueFromDB, testIssue);
//
//            assertTrue(issuesDao.delete(testIssue.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void addNullablePeriodicalIssue() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            issuesDao.add(null);
//        });
//    }
//
//    @Test
//    void addPeriodicalIssueWithNonNullKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            testIssue.setId(111);
//            issuesDao.add(testIssue);
//        });
//    }
//
//    @Test
//    void addSamePeriodicalIssueTwice() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            issuesDao.add(testIssue);
//            issuesDao.add(testIssue);
//        });
//
//        try {
//            assertTrue(issuesDao.delete(testIssue.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getByValidPK() {
//        try {
//            sameIssueFromDB = issuesDao.getByKey(insertedIssue.getId());
//
//            assertNotNull(sameIssueFromDB);
//            assertEquals(sameIssueFromDB, insertedIssue);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getByInvalidPK() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            issuesDao.getByKey(111);
//        });
//    }
//
//    @Test
//    void getByNullableKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            issuesDao.getByKey(null);
//        });
//    }
//
//    @Test
//    void updateExistingPeriodicalIssue() {
//        try {
//            sameIssueFromDB = issuesDao.getByKey(insertedIssue.getId());
//            assertNotNull(sameIssueFromDB);
//
//            String newName = "new name";
//            int newPeriodicalId = 1;
//
//            sameIssueFromDB.setName(newName);
//            sameIssueFromDB.setPeriodicalId(1);
//            issuesDao.update(sameIssueFromDB);
//
//            sameIssueFromDB = issuesDao.getByKey(insertedIssue.getId());
//            assertNotEquals(sameIssueFromDB, insertedIssue);
//            assertEquals(newName, sameIssueFromDB.getName());
//            assertTrue(newPeriodicalId == sameIssueFromDB.getPeriodicalId());
//
//            issuesDao.update(insertedIssue);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void updateNonExistingPeriodicalIssue() {
//        try {
//            assertFalse(issuesDao.update(nonExistingIssue));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void updateNullablePeriodicalIssue() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            issuesDao.update(null);
//        });
//    }
//
//    @Test
//    void deleteExistingPeriodicalIssue() {
//        try {
//            issuesDao.add(testIssue);
//            assertTrue(testIssue.getId() != 0);
//
//            boolean deleted = issuesDao.delete(testIssue.getId());
//            assertTrue(deleted);
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    void deleteNonExistingPeriodicalIssue() {
//        try {
//            assertFalse(issuesDao.delete(nonExistingIssue.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void deleteNullablePeriodicalIssue() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            issuesDao.delete(null);
//        });
//    }
//
//    @Test
//    void deleteNotIdentifiedPeriodicalIssue() {
//        assertNull(testIssue.getId());
//        Assertions.assertThrows(DaoException.class, () -> {
//            issuesDao.delete(testIssue.getId());
//        });
//    }
//}