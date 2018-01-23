package com.periodicals.dao.jdbc;

import com.mysql.cj.core.exceptions.AssertionFailedException;
import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.exceptions.DaoException;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.H2Manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UsersJdbcDAOTest {
    //    static User testUser1, testUser2, nonExistingUser, sameUserFromDB;
    static Logger log = Logger.getLogger(UsersJdbcDAOTest.class.getSimpleName());
    //    static final String USERS_TABLE_NAME = "users";
    private static H2Manager testDbManager = H2Manager.getInstance();
    private static UsersJdbcDao usersDAO;
    int genId;

    @BeforeAll
    static void beforeAll() {
        testDbManager.insertDefaultData();
        usersDAO = (UsersJdbcDao) JdbcDaoFactory.getInstance().getUsersDao();
    }

    @Test
    void getAllUsers() {
        try {
            int expected = 3;
            int count = usersDAO.getEntitiesCount();
            assertEquals(expected, count);
        } catch (DaoException e) {
            throw new AssertionFailedException(e.getMessage());
        }
    }
//    void setUp() {
//////            genId = 0;
//////            conn = inmManager.getConnection();
//////            usersDAO = (UsersJdbcDao2) JdbcDaoFactory.getInstance().getUsersDao();
////    }
////    @BeforeAll
////    static void BeforeAll() {
////        try {
////            nonExistingUser = new User("non existing login", "non existing pass");
////            nonExistingUser.setId(112);
////
////            testUser1 = new User("Peter", "123456", "waytopeter@gmail.com");
////            testUser2 = new User("Mariya", "1234567");
////        } catch (SQLException | IllegalArgumentException e) {
////            log.error(e.getMessage());
////            fail(e.getMessage());
////        }
////    }
//
////    @AfterAll
////    static void AfterAll() {
//////        usersDAO = null;
//////        inmManager = null;
//////        testUser1 = null;
//////        testUser2 = null;
//////        nonExistingUser = null;
//////        sameUserFromDB = null;
////    }
////
////    @BeforeEach
////    void setUp() {
//////            genId = 0;
//////            conn = inmManager.getConnection();
//////            usersDAO = (UsersJdbcDao2) JdbcDaoFactory.getInstance().getUsersDao();
////    }
////
//    @AfterEach
//    void tearDown() {
//        testUser1.setId(null);
//        testUser2.setId(null);
//        sameUserFromDB = null;
//        try {
//            conn.close();
////            inmManager.truncateTable(USERS_TABLE_NAME);
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }

//    @Test
//    void addUser() {
//        try {
//            List<User> usersBefore = usersDAO.getAllGenres();
//            assertTrue(usersBefore.size() == 0);
//
//            usersDAO.addNewIssue(testUser1);
//            List<User> usersAfter = usersDAO.getAllGenres();
//
//            assertTrue(testUser1.getId() != 0);
//            sameUserFromDB = usersDAO.getById(testUser1.getId());
//
//            assertNotNull(sameUserFromDB);
//            assertEquals(testUser1, sameUserFromDB);
//            assertEquals(1, usersAfter.size());
//            assertEquals(testUser1, sameUserFromDB);
//
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void addNullableUser() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            usersDAO.addNewIssue(null);
//        });
//    }
//
//    @Test
//    void addUserWithNonNullKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            testUser1.setId(1);
//            usersDAO.addNewIssue(testUser1);
//        });
//    }
//
//    @Test
//    void addUserWithSameLogin() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            usersDAO.addNewIssue(testUser1);
//            usersDAO.addNewIssue(new User(testUser1.getLogin(), "pass"));
//        });
//    }
//
//    @Test
//    void addSameUserTwice() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            usersDAO.addNewIssue(testUser1);
//            usersDAO.addNewIssue(testUser1);
//        });
//    }
//
//    @Test
//    void getByValidPK() {
//        try {
//            usersDAO.addNewIssue(testUser1);
//            assertNotNull(testUser1.getId());
//
//            sameUserFromDB = usersDAO.getById(testUser1.getId());
//
//            assertNotNull(sameUserFromDB);
//            assertEquals(testUser1, sameUserFromDB);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getByInvalidPK() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            User result = usersDAO.getById(11);
//        });
//    }
//
//    @Test
//    void getByNullableKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            User result = usersDAO.getById(null);
//        });
//    }
//
//    @Test
//    void updateExistingUser() {
//        try {
//            genId = usersDAO.addNewIssue(testUser1);
//            assertTrue(genId != 0);
//
//            sameUserFromDB = usersDAO.getById(genId);
//            assertEquals(testUser1, sameUserFromDB);
//
//            String newPass = "11";
//            sameUserFromDB.setPassword(newPass);
//            usersDAO.update(sameUserFromDB);
//
//            sameUserFromDB = usersDAO.getById(genId);
//            assertNotEquals(sameUserFromDB, testUser1);
//            assertEquals(newPass, sameUserFromDB.getPassword());
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void updateNonExistingUser() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            usersDAO.update(nonExistingUser);
//        });
//    }
//
//    @Test
//    void updateNullableUser() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            usersDAO.update(null);
//        });
//    }
//
//    @Test
//    void deleteExistingUser() {
//        try {
//            genId = usersDAO.addNewIssue(testUser1);
//            assertTrue(genId != 0);
//
//            int expectedTableSize = 1;
//            int resultTableSize = usersDAO.getAllGenres().size();
//            assertEquals(expectedTableSize, resultTableSize);
//
//            sameUserFromDB = usersDAO.getById(genId);
//            assertEquals(testUser1, sameUserFromDB);
//
//            usersDAO.deletePeriodicalById(sameUserFromDB.getId());
//
//            expectedTableSize = 0;
//            resultTableSize = usersDAO.getAllGenres().size();
//            assertEquals(expectedTableSize, resultTableSize);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void deleteNonExistingUser() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            usersDAO.deletePeriodicalById(nonExistingUser.getId());
//        });
//    }
//
//    @Test
//    void deleteNullableUser() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            usersDAO.deletePeriodicalById(null);
//        });
//    }
//
//    @Test
//    void deleteNotIdentifiedUser() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            usersDAO.deletePeriodicalById(testUser2.getId());
//        });
//    }
//
//    @Test
//    void getExistingUserByLogin() {
//        try {
//            genId = usersDAO.addNewIssue(testUser1);
//            assertTrue(genId != 0);
//
//            sameUserFromDB = usersDAO.getByLogin(testUser1.getLogin());
//            assertNotNull(sameUserFromDB);
//            assertEquals(testUser1, sameUserFromDB);
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void getNonExistingUserByLogin() {
//        try {
//            int tableSize = usersDAO.getAllGenres().size();
//            assertTrue(tableSize == 0);
//
//            Assertions.assertThrows(DaoException.class, () -> {
//                usersDAO.getByLogin(nonExistingUser.getLogin());
//            });
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getUserByNullableLogin() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            usersDAO.getByLogin(null);
//        });
//    }
}