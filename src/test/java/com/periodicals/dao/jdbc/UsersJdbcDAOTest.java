//package com.periodicals.dao.jdbc;
//
//import com.periodicals.dao.factories.JdbcDaoFactory;
//import com.periodicals.exceptions.DaoException;
//import com.periodicals.dao.entities.User;
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
//class UsersJdbcDAOTest {
//    static final String USERS_TABLE_NAME = "users";
//    static InMemoryDbManager inmManager;
//    static UsersJdbcDao usersDAO;
//    static User testUser1, testUser2, nonExistingUser, sameUserFromDB;
//    static Connection conn;
//
//    static Logger log = Logger.getLogger(UsersJdbcDAOTest.class.getSimpleName());
//
//    static {
//        new DOMConfigurator().doConfigure("src\\main\\resources\\log4j.xml", LogManager.getLoggerRepository());
//    }
//
//    int genId;
//
//    @BeforeAll
//    static void BeforeAll() {
//        try {
//            inmManager = new InMemoryDbManager();
//
//            nonExistingUser = new User("non existing login", "non existing pass");
//            nonExistingUser.setId(112);
//
//            testUser1 = new User("Peter", "123456", "waytopeter@gmail.com");
//            testUser2 = new User("Mariya", "1234567");
//        } catch (SQLException | IllegalArgumentException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @AfterAll
//    static void AfterAll() {
//        usersDAO = null;
//        inmManager = null;
//        testUser1 = null;
//        testUser2 = null;
//        nonExistingUser = null;
//        sameUserFromDB = null;
//    }
//
//    @BeforeEach
//    void setUp() {
//            genId = 0;
//            conn = inmManager.getConnection();
//            usersDAO = (UsersJdbcDao) JdbcDaoFactory.getInstance().getUsersDao();
//    }
//
//    @AfterEach
//    void tearDown() {
//        testUser1.setId(null);
//        testUser2.setId(null);
//        sameUserFromDB = null;
//        try {
//            conn.close();
//            inmManager.truncateTable(USERS_TABLE_NAME);
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void addUser() {
//        try {
//            List<User> usersBefore = usersDAO.getAll();
//            assertTrue(usersBefore.size() == 0);
//
//            usersDAO.add(testUser1);
//            List<User> usersAfter = usersDAO.getAll();
//
//            assertTrue(testUser1.getId() != 0);
//            sameUserFromDB = usersDAO.getByKey(testUser1.getId());
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
//            usersDAO.add(null);
//        });
//    }
//
//    @Test
//    void addUserWithNonNullKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            testUser1.setId(1);
//            usersDAO.add(testUser1);
//        });
//    }
//
//    @Test
//    void addUserWithSameLogin() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            usersDAO.add(testUser1);
//            usersDAO.add(new User(testUser1.getLogin(), "pass"));
//        });
//    }
//
//    @Test
//    void addSameUserTwice() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            usersDAO.add(testUser1);
//            usersDAO.add(testUser1);
//        });
//    }
//
//    @Test
//    void getByValidPK() {
//        try {
//            usersDAO.add(testUser1);
//            assertNotNull(testUser1.getId());
//
//            sameUserFromDB = usersDAO.getByKey(testUser1.getId());
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
//            User result = usersDAO.getByKey(11);
//        });
//    }
//
//    @Test
//    void getByNullableKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            User result = usersDAO.getByKey(null);
//        });
//    }
//
//    @Test
//    void updateExistingUser() {
//        try {
//            genId = usersDAO.add(testUser1);
//            assertTrue(genId != 0);
//
//            sameUserFromDB = usersDAO.getByKey(genId);
//            assertEquals(testUser1, sameUserFromDB);
//
//            String newPass = "11";
//            sameUserFromDB.setPassword(newPass);
//            usersDAO.update(sameUserFromDB);
//
//            sameUserFromDB = usersDAO.getByKey(genId);
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
//            genId = usersDAO.add(testUser1);
//            assertTrue(genId != 0);
//
//            int expectedTableSize = 1;
//            int resultTableSize = usersDAO.getAll().size();
//            assertEquals(expectedTableSize, resultTableSize);
//
//            sameUserFromDB = usersDAO.getByKey(genId);
//            assertEquals(testUser1, sameUserFromDB);
//
//            usersDAO.delete(sameUserFromDB.getId());
//
//            expectedTableSize = 0;
//            resultTableSize = usersDAO.getAll().size();
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
//            usersDAO.delete(nonExistingUser.getId());
//        });
//    }
//
//    @Test
//    void deleteNullableUser() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            usersDAO.delete(null);
//        });
//    }
//
//    @Test
//    void deleteNotIdentifiedUser() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            usersDAO.delete(testUser2.getId());
//        });
//    }
//
//    @Test
//    void getExistingUserByLogin() {
//        try {
//            genId = usersDAO.add(testUser1);
//            assertTrue(genId != 0);
//
//            sameUserFromDB = usersDAO.getUserByLogin(testUser1.getLogin());
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
//            int tableSize = usersDAO.getAll().size();
//            assertTrue(tableSize == 0);
//
//            Assertions.assertThrows(DaoException.class, () -> {
//                usersDAO.getUserByLogin(nonExistingUser.getLogin());
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
//            usersDAO.getUserByLogin(null);
//        });
//    }
//}