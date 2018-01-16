package com.periodicals.dao.connection;

import java.sql.Connection;
import java.util.Objects;

/*TODO make one method instead of 2*/
public class ConnectionManager {
    private static ThreadLocal<ConnectionWrapper> threadLocal = new ThreadLocal<>();
    private static ConnectionPool pool = ConnectionPool.getInstance();

    public static ThreadLocal<ConnectionWrapper> getThreadLocal(){
        return threadLocal;
    }

    public static ConnectionWrapper getConnectionWrapper() {
        if (Objects.isNull(threadLocal.get())) {
            Connection conn = pool.getConnection();
            return new ConnectionWrapper(conn, false);
        } else {
            return threadLocal.get();
        }
    }

    public static ConnectionWrapper getTransactionConnectionWrapper() {
        if (Objects.isNull(threadLocal.get())) {
            Connection conn = pool.getConnection();
            return new ConnectionWrapper(conn, true);
        } else {
            return threadLocal.get();
        }
    }
}
