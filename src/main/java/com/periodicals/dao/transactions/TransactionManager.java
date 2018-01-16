package com.periodicals.dao.transactions;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.exceptions.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class TransactionManager {
    private static ThreadLocal<ConnectionWrapper> threadLocal = ConnectionManager.getThreadLocal();

    private TransactionManager() {

    }

    static void beginTransaction() throws SQLException, TransactionException {
        if (Objects.nonNull(threadLocal.get())) {
            throw new TransactionException("Attempt to begin transaction with opened connection");
        }
        ConnectionWrapper wrapper = ConnectionManager.getTransactionConnectionWrapper();
        wrapper.getConnection().setAutoCommit(false);
        threadLocal.set(wrapper);
    }

    static void endTransaction() throws TransactionException {
        if (Objects.isNull(threadLocal.get())) {
            throw new TransactionException("Attempt to close transaction on nullable connection");
        }
        ConnectionWrapper wrapper = threadLocal.get();
        Connection conn = wrapper.getConnection();
        try {
            conn.commit();
            conn.setAutoCommit(true);
            conn.close();
        } catch (SQLException e) {
            throw new TransactionException("Failed to commit or close connection");
        } finally {
            threadLocal.set(null);
        }
    }

    static void doRollback() throws TransactionException {
        if (Objects.isNull(threadLocal.get())) {
            throw new TransactionException("Attempt to doRollback not existing connection");
        }
        ConnectionWrapper wrapper = threadLocal.get();
        Connection conn = wrapper.getConnection();
        try {
            conn.rollback();
            conn.setAutoCommit(true);
            conn.close();
        } catch (SQLException e) {
            throw new TransactionException("Failed to rollback transaction");
        } finally {
            threadLocal.set(null);
        }
    }

//    public static ConnectionWrapper getConnectionWrapper() {
//        if (Objects.isNull(threadLocal.get())) {
//            Connection connection = ConnectionPool.getInstance().getConnectionWrapper();
//            return new ConnectionWrapper(connection, false);
//        } else {
//            return threadLocal.get();
//        }
//    }
}
