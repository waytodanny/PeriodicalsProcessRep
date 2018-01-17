//package com.periodicals.dao.jdbc;
//
//import com.periodicals.dao.factories.JdbcDaoFactory;
//import com.periodicals.exceptions.DaoException;
//import com.periodicals.entities.Periodical;
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
//class PeriodicalsJdbcDAOTest {
//    private static InMemoryDbManager inmManager;
//    private static PeriodicalsJdbcDao persDAO;
//    private static Periodical
//            testPeriodical, nonExistingPeriodical, samePeriodicalFromDB, insertedPeriodical;
//
//    private static Connection conn;
//
//    private static Logger log = Logger.getLogger(PeriodicalsJdbcDAOTest.class.getSimpleName());
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
//            nonExistingPeriodical = new Periodical(
//                    "non existing name", "secret description", 0, (short) 1, 1);
//            nonExistingPeriodical.setId(112);
//
//            testPeriodical = new Periodical(
//                    "TMNT", "Ninja turtles story", 10.56, (short) 1, 2);
//        } catch (SQLException | IllegalArgumentException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @AfterAll
//    static void AfterAll() {
//        inmManager = null;
//        persDAO = null;
//        testPeriodical = null;
//        nonExistingPeriodical = null;
//        samePeriodicalFromDB = null;
//    }
//
//    @BeforeEach
//    void setUp() {
//        try {
//            conn = inmManager.getConnection();
//            persDAO = (PeriodicalsJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalsDao();
//            insertedPeriodical = persDAO.getById(1);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @AfterEach
//    void tearDown() {
//        testPeriodical.setId(null);
//        samePeriodicalFromDB = null;
//        try {
//            conn.close();
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void addPeriodical() {
//        try {
//            persDAO.add(testPeriodical);
//            assertTrue(testPeriodical.getId() != 0);
//
//            samePeriodicalFromDB = persDAO.getById(testPeriodical.getId());
//
//            assertNotNull(samePeriodicalFromDB);
//            assertEquals(testPeriodical, samePeriodicalFromDB);
//
//            assertTrue(persDAO.delete(testPeriodical.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void addNullablePeriodical() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            persDAO.add(null);
//        });
//    }
//
//    @Test
//    void addPeriodicalWithNonNullKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            testPeriodical.setId(111);
//            persDAO.add(testPeriodical);
//        });
//    }
//
//    @Test
//    void addSamePeriodicalTwice() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            persDAO.add(testPeriodical);
//            persDAO.add(testPeriodical);
//        });
//
//        try {
//            assertTrue(persDAO.delete(testPeriodical.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getByValidPK() {
//        try {
//            samePeriodicalFromDB = persDAO.getById(insertedPeriodical.getId());
//
//            assertNotNull(samePeriodicalFromDB);
//            assertEquals(insertedPeriodical, samePeriodicalFromDB);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getByInvalidPK() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            persDAO.getById(111);
//        });
//    }
//
//    @Test
//    void getByNullableKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            persDAO.getById(null);
//        });
//    }
//
//    @Test
//    void updateExistingPeriodical() {
//        try {
//            samePeriodicalFromDB = persDAO.getById(insertedPeriodical.getId());
//            assertNotNull(samePeriodicalFromDB);
//
//            String newName = "new name";
//            String newDescription = "new desc";
//            Double newCost = 7.50;
//            short newGenreId = 2;
//
//            samePeriodicalFromDB.setName(newName);
//            samePeriodicalFromDB.setDescription(newDescription);
//            samePeriodicalFromDB.setSubscriptionCost(newCost);
//            samePeriodicalFromDB.setGenreId(newGenreId);
//
//            persDAO.update(samePeriodicalFromDB);
//
//            samePeriodicalFromDB = persDAO.getById(insertedPeriodical.getId());
//            assertNotEquals(samePeriodicalFromDB, testPeriodical);
//            assertEquals(newName, samePeriodicalFromDB.getName());
//            assertEquals(newDescription, samePeriodicalFromDB.getDescription());
//            assertEquals(newCost, samePeriodicalFromDB.getSubscriptionCost());
//            assertTrue(newGenreId == samePeriodicalFromDB.getGenreId());
//
//            persDAO.update(insertedPeriodical);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void updateNonExistingPeriodical() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            persDAO.update(nonExistingPeriodical);
//        });
//    }
//
//    @Test
//    void updateNullablePeriodical() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            persDAO.update(null);
//        });
//    }
//
//    @Test
//    void deleteExistingPeriodicalWithForeignConstraints() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            persDAO.delete(insertedPeriodical.getId());
//        });
//    }
//
//    @Test
//    void deleteExistingPeriodicalWithoutForeignConstraints() {
//        try {
//            persDAO.add(testPeriodical);
//            assertTrue(testPeriodical.getId() != 0);
//
//            boolean deleted = persDAO.delete(testPeriodical.getId());
//            assertTrue(deleted);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void deleteNonExistingPeriodical() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            persDAO.delete(nonExistingPeriodical.getId());
//        });
//    }
//
//    @Test
//    void deleteNullablePeriodical() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            persDAO.delete(null);
//        });
//    }
//
//    @Test
//    void deleteNotIdentifiedPeriodical() {
//        assertNull(testPeriodical.getId());
//        Assertions.assertThrows(DaoException.class, () -> {
//            persDAO.delete(testPeriodical.getId());
//        });
//    }
//
//    @Test
//    void getValidUserSubscriptions() {
//        /*Info from insertDefaultsScript*/
//        final int userId = 3;
//        final int expectedSize = 3;
//        try {
//            List<Periodical> pers = persDAO.getUserSubscriptions(userId);
//            assertNotNull(pers);
//            assertTrue(pers.size() == expectedSize);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getInvalidUserSubscriptions() {
//        final int userId = 111;
//        final int expectedSize = 0;
//        try {
//            List<Periodical> pers = persDAO.getUserSubscriptions(userId);
//            assertTrue(pers.size() == expectedSize);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getUserSubscriptionsByNullableId() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            persDAO.getUserSubscriptions(null);
//        });
//    }
//}