//package com.periodicals.dao.jdbc;
//
//import com.periodicals.dao.interfaces.GenericDao;
//import com.periodicals.entities.util.Identified;
//import dbTestHelpers.InMemoryDbManager;
//import org.apache.log4j.LogManager;
//import org.apache.log4j.Logger;
//import org.apache.log4j.xml.DOMConfigurator;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//abstract class AbstractJdbcDAOTest<T extends Identified<K>, K> {
//    private static Logger log = Logger.getLogger(PeriodicalIssuesJdbcDAOTest.class.getSimpleName());
//
//    static {
//        new DOMConfigurator().doConfigure("src\\main\\resources\\log4j.xml", LogManager.getLoggerRepository());
//    }
//
//    protected static InMemoryDbManager inmManager;
//    protected static GenericDao dao;
//    protected T testObject, nonExistingObject, sameObjectFromDB, insertedObject;
//
//    protected static Connection conn;
//
//    @BeforeAll
//    static void BeforeAll() {
//        try {
//            inmManager = new InMemoryDbManager();
//            inmManager.insertDefaultData();
//        } catch (SQLException | IllegalArgumentException e) {
//            log.error(e.getMessage());
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    void getAll() {
//    }
//
//    @Test
//    void addObject() {
//
//    }
//
//    @Test
//    void addNullableObject() {
//
//    }
//
//    @Test
//    void addObjectWithNonNullKey() {
//
//    }
//
//    @Test
//    void addSameObjectTwice() {
//
//    }
//
//    @Test
//    void getByValidPK() {
//
//    }
//
//    @Test
//    void getByInvalidPK() {
//
//    }
//
//    @Test
//    void getByNullableKey() {
//
//    }
//
//    @Test
//    void updateExistingObject() {
//
//    }
//
//    @Test
//    void updateNonExistingObject() {
//
//    }
//
//    @Test
//    void updateNullableObject() {
//
//    }
//
//    @Test
//    void deleteExistingObject() {
//
//    }
//
//    @Test
//    void deleteNonExistingObject() {
//
//    }
//
//    @Test
//    void deleteNullableObject() {
//
//    }
//
//    @Test
//    void deleteNotIdentifiedObject() {
//
//    }
//}