//package com.periodicals.dao.jdbc;
//
//import com.periodicals.dao.entities.Genre;
//import com.periodicals.exceptions.DaoException;
//import com.periodicals.dao.factories.JdbcDaoFactory;
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
//class GenresJdbcDAOTest {
//    private static InMemoryDbManager inmManager;
//    private static GenresJdbcDao genresDao;
//    private static Genre
//            testGenre, nonExistingGenre, sameGenreFromDB, insertedGenre;
//
//    private static Connection conn;
//
//    private static Logger log = Logger.getLogger(GenresJdbcDAOTest.class.getSimpleName());
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
//            testGenre = new Genre("Massacre");
//            nonExistingGenre = new Genre("Adventures");
//            nonExistingGenre.setId((short) 112);
//        } catch (SQLException | IllegalArgumentException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @AfterAll
//    static void AfterAll() {
//        inmManager = null;
//        genresDao = null;
//        testGenre = null;
//        nonExistingGenre = null;
//        insertedGenre = null;
//    }
//
//    @BeforeEach
//    void setUp() {
//        try {
//            conn = inmManager.getConnection();
//            genresDao = (GenresJdbcDao) JdbcDaoFactory.getInstance().getGenresDao();
//            insertedGenre = genresDao.getById((short) 1);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @AfterEach
//    void tearDown() {
//        testGenre.setId(null);
//        sameGenreFromDB = null;
//        try {
//            conn.close();
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void addPeriodicalGenre() {
//        try {
//            genresDao.add(testGenre);
//            assertTrue(testGenre.getId() != 0);
//
//            sameGenreFromDB = genresDao.getById(testGenre.getId());
//
//            assertNotNull(sameGenreFromDB);
//            assertEquals(sameGenreFromDB, testGenre);
//
//            assertTrue(genresDao.delete(testGenre.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void addNullablePeriodicalGenre() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            genresDao.add(null);
//        });
//    }
//
//    @Test
//    void addPeriodicalGenreWithNonNullKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            testGenre.setId((short) 111);
//            genresDao.add(testGenre);
//        });
//    }
//
//    @Test
//    void addSamePeriodicalGenreTwice() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            genresDao.add(testGenre);
//            genresDao.add(testGenre);
//        });
//
//        try {
//            assertTrue(genresDao.delete(testGenre.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getByValidPK() {
//        try {
//            sameGenreFromDB = genresDao.getById(insertedGenre.getId());
//
//            assertNotNull(sameGenreFromDB);
//            assertEquals(sameGenreFromDB, insertedGenre);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getByInvalidPK() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            genresDao.getById((short) 111);
//        });
//    }
//
//    @Test
//    void getByNullableKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            genresDao.getById(null);
//        });
//    }
//
//    @Test
//    void updateExistingPeriodicalGenre() {
//        try {
//            sameGenreFromDB = genresDao.getById(insertedGenre.getId());
//            assertNotNull(sameGenreFromDB);
//
//            String newName = "marvel vol.4";
//
//            sameGenreFromDB.setName(newName);
//            genresDao.update(sameGenreFromDB);
//
//            sameGenreFromDB = genresDao.getById(insertedGenre.getId());
//            assertNotEquals(sameGenreFromDB, insertedGenre);
//            assertEquals(newName, sameGenreFromDB.getName());
//
//            genresDao.update(insertedGenre);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void updateNonExistingPeriodicalGenre() {
//        try {
//            assertFalse(genresDao.update(nonExistingGenre));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void updateNullablePeriodicalGenre() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            genresDao.update(null);
//        });
//    }
//
//    @Test
//    void deleteExistingPeriodicalGenre() {
//        try {
//            genresDao.add(testGenre);
//            assertTrue(testGenre.getId() != 0);
//
//            boolean deleted = genresDao.delete(testGenre.getId());
//            assertTrue(deleted);
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    void deleteNonExistingPeriodicalGenre() {
//        try {
//            assertFalse(genresDao.delete(nonExistingGenre.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void deleteNullablePeriodicalGenre() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            genresDao.delete(null);
//        });
//    }
//
//    @Test
//    void deleteNotIdentifiedPeriodicalGenre() {
//        assertNull(testGenre.getId());
//        Assertions.assertThrows(DaoException.class, () -> {
//            genresDao.delete(testGenre.getId());
//        });
//    }
//}