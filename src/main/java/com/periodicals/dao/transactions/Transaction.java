package com.periodicals.dao.transactions;

import com.periodicals.exceptions.DaoException;
import com.periodicals.exceptions.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface Transaction {
//    Logger LOGGER = LogManager.getLogger(Transaction.class);

    static void doTransaction(Transaction tx, int transactionIsolationLevel) throws TransactionException {
        try {
            TransactionManager.beginTransaction();
            tx.pass();
            TransactionManager.endTransaction();
        } catch (TransactionException | DaoException | SQLException e) {
            TransactionManager.doRollback();
            throw new TransactionException("Transaction did rollback: " + e);
        }
    }

    static void doTransaction(Transaction tx) throws TransactionException {
        doTransaction(tx, Connection.TRANSACTION_READ_COMMITTED);
    }

    void pass() throws DaoException;
}
