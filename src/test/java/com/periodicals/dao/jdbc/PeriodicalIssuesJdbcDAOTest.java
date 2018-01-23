//package com.periodicals.dao.jdbc;
//
//import com.periodicals.exceptions.DaoException;
//import com.periodicals.dao.factories.JdbcDaoFactory;
//import com.periodicals.entities.PeriodicalIssue;
//import util.InMemoryDbManager;
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
//            insertedIssue = issuesDao.getById(1);
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
//            issuesDao.addNewIssue(testIssue);
//            assertTrue(testIssue.getId() != 0);
//
//            sameIssueFromDB = issuesDao.getById(testIssue.getId());
//
//            assertNotNull(sameIssueFromDB);
//
//            testIssue.setPublishDate(sameIssueFromDB.getPublishDate());
//            assertEquals(sameIssueFromDB, testIssue);
//
//            assertTrue(issuesDao.deletePeriodicalById(testIssue.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void addNullablePeriodicalIssue() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            issuesDao.addNewIssue(null);
//        });
//    }
//
//    @Test
//    void addPeriodicalIssueWithNonNullKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            testIssue.setId(111);
//            issuesDao.addNewIssue(testIssue);
//        });
//    }
//
//    @Test
//    void addSamePeriodicalIssueTwice() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            issuesDao.addNewIssue(testIssue);
//            issuesDao.addNewIssue(testIssue);
//        });
//
//        try {
//            assertTrue(issuesDao.deletePeriodicalById(testIssue.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getByValidPK() {
//        try {
//            sameIssueFromDB = issuesDao.getById(insertedIssue.getId());
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
//            issuesDao.getById(111);
//        });
//    }
//
//    @Test
//    void getByNullableKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            issuesDao.getById(null);
//        });
//    }
//
//    @Test
//    void updateExistingPeriodicalIssue() {
//        try {
//            sameIssueFromDB = issuesDao.getById(insertedIssue.getId());
//            assertNotNull(sameIssueFromDB);
//
//            String newName = "new name";
//            int newPeriodicalId = 1;
//
//            sameIssueFromDB.setName(newName);
//            sameIssueFromDB.setPeriodicalId(1);
//            issuesDao.update(sameIssueFromDB);
//
//            sameIssueFromDB = issuesDao.getById(insertedIssue.getId());
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
//            issuesDao.addNewIssue(testIssue);
//            assertTrue(testIssue.getId() != 0);
//
//            boolean deleted = issuesDao.deletePeriodicalById(testIssue.getId());
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
//            assertFalse(issuesDao.deletePeriodicalById(nonExistingIssue.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void deleteNullablePeriodicalIssue() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            issuesDao.deletePeriodicalById(null);
//        });
//    }
//
//    @Test
//    void deleteNotIdentifiedPeriodicalIssue() {
//        assertNull(testIssue.getId());
//        Assertions.assertThrows(DaoException.class, () -> {
//            issuesDao.deletePeriodicalById(testIssue.getId());
//        });
//    }
//}