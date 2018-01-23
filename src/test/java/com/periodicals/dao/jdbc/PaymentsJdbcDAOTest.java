//package com.periodicals.dao.jdbc;
//
//import com.periodicals.exceptions.DaoException;
//import com.periodicals.dao.factories.JdbcDAOFactory;
//import com.periodicals.entities.Payment;
//import util.InMemoryDbManager;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.apache.log4j.xml.DOMConfigurator;
//import org.junit.jupiter.api.*;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PaymentsJdbcDAOTest {
//    private static final Long INSERTED_KEY = 1L;
//    private static final Long INVALID_PK = 111L;
//    private static InMemoryDbManager inmManager;
//    private static PaymentsJdbcDAO paymentsDao;
//    private static Payment
//            testPayment, nonExistingPayment, samePaymentFromDB, insertedPayment;
//    private static List<Integer> testPerIds;
//    private static Connection conn;
//
//    private static Logger log = Logger.getLogger(PaymentsJdbcDAOTest.class.getSimpleName());
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
//            testPerIds = new ArrayList<>();
//            testPerIds.addNewIssue(1); testPerIds.addNewIssue(2); testPerIds.addNewIssue(3);
//            testPayment = new Payment(2, 20.5);
//            testPayment.setPeriodicalsId(testPerIds);
//
//            nonExistingPayment = new Payment(2, 12.6);
//            nonExistingPayment.setId(112L);
//        } catch (SQLException | IllegalArgumentException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @AfterAll
//    static void AfterAll() {
//        inmManager = null;
//        paymentsDao = null;
//        testPayment = null;
//        nonExistingPayment = null;
//        insertedPayment = null;
//    }
//
//    @BeforeEach
//    void setUp() {
//        try {
//            conn = inmManager.getConnection();
//            paymentsDao = (PaymentsJdbcDAO) JdbcDAOFactory.getInstance().getPaymentsDao();
//            insertedPayment = paymentsDao.getById(INSERTED_KEY);
//            insertedPayment.setPeriodicalsId(paymentsDao.getPaymentPeriodicals(insertedPayment.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @AfterEach
//    void tearDown() {
//        testPayment.setId(null);
//        samePaymentFromDB = null;
//        try {
//            conn.close();
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void addPayment() {
//        try {
//            paymentsDao.addNewIssue(testPayment);
//            assertTrue(testPayment.getId() != 0);
//
//            samePaymentFromDB = paymentsDao.getById(testPayment.getId());
//
//            assertNotNull(samePaymentFromDB);
//
//            testPayment.setPaymentTime(samePaymentFromDB.getPaymentTime());
//            assertEquals(samePaymentFromDB, testPayment);
//
//            assertTrue(paymentsDao.deletePeriodicalById(testPayment.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void addNullablePayment() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            paymentsDao.addNewIssue(null);
//        });
//    }
//
//    @Test
//    void addPaymentWithNonNullKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            testPayment.setId(INSERTED_KEY);
//            paymentsDao.addNewIssue(testPayment);
//        });
//    }
//
//    @Test
//    void addSamePaymentTwice() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            paymentsDao.addNewIssue(testPayment);
//            paymentsDao.addNewIssue(testPayment);
//        });
//
//        try {
//            assertTrue(paymentsDao.deletePeriodicalById(testPayment.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getByValidPK() {
//        try {
//            samePaymentFromDB = paymentsDao.getById(insertedPayment.getId());
//
//            assertNotNull(samePaymentFromDB);
//            assertEquals(samePaymentFromDB, insertedPayment);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getByInvalidPK() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            paymentsDao.getById(INVALID_PK);
//        });
//    }
//
//    @Test
//    void getByNullableKey() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            paymentsDao.getById(null);
//        });
//    }
//
//    @Test
//    void deleteExistingPayment() {
//        try {
//            paymentsDao.addNewIssue(testPayment);
//            assertTrue(testPayment.getId() != 0);
//
//            boolean deleted = paymentsDao.deletePeriodicalById(testPayment.getId());
//            assertTrue(deleted);
//        } catch (DaoException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Test
//    void deleteNonExistingPayment() {
//        try {
//            assertFalse(paymentsDao.deletePeriodicalById(nonExistingPayment.getId()));
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void deleteNullablePayment() {
//        Assertions.assertThrows(DaoException.class, () -> {
//            paymentsDao.deletePeriodicalById(null);
//        });
//    }
//
//    @Test
//    void deleteNotIdentifiedPayment() {
//        assertNull(testPayment.getId());
//        Assertions.assertThrows(DaoException.class, () -> {
//            paymentsDao.deletePeriodicalById(testPayment.getId());
//        });
//    }
//
//    @Test
//    void getExistingPaymentPeriodicals(){
//        try {
//            int expectedSize = 2;
//            List<Integer> resultList = paymentsDao.getPaymentPeriodicals(insertedPayment.getId());
//            int resultSize = resultList.size();
//
//            assertEquals(expectedSize, resultSize);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getNonExistingPaymentPeriodicals(){
//        try {
//            int expectedSize = 0;
//            List<Integer> resultList = paymentsDao.getPaymentPeriodicals(testPayment.getId());
//            int resultSize = resultList.size();
//
//            assertEquals(expectedSize, resultSize);
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getNullablePaymentPeriodicals(){
//        Assertions.assertThrows(DaoException.class, () -> {
//            paymentsDao.getPaymentPeriodicals(null);
//        });
//    }
//
//    @Test
//    void deleteExistingPaymentPeriodicals(){
//        System.out.println();
//    }
//
//    @Test
//    void addPaymentPeriodicals(){
//        try {
//            paymentsDao.addNewIssue(testPayment);
//            assertNotNull(testPayment.getId());
//
//            paymentsDao.addPaymentPeriodicals(testPayment);
//            List<Integer> resultIds = paymentsDao.getPaymentPeriodicals(testPayment.getId());
//
//            int expectedSize = testPerIds.size();
//            int resultSize = resultIds.size();
//
//            assertEquals(expectedSize, resultSize);
//            assertEquals(testPerIds, resultIds);
//
//            paymentsDao.deletePaymentPeriodicals(testPayment.getId());
//            paymentsDao.deletePeriodicalById(testPayment.getId());
//        } catch (DaoException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//}