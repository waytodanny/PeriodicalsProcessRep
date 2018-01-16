package com.periodicals.dao.jdbc;

import com.periodicals.dao.interfaces.PaymentsDao;
import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.dao.entities.Payment;
import com.periodicals.dao.entities.Periodical;
import com.periodicals.dao.entities.User;
import com.periodicals.exceptions.DaoException;

import javax.naming.OperationNotSupportedException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentsJdbcDao extends AbstractJdbcDao<Payment, Long> implements PaymentsDao {
    public static final String SELECT_USER_PAYMENTS_SUBLIST =
            "SELECT id, payment_time,  payment_sum, user_id FROM payments WHERE user_id = ? LIMIT ?,?;";
    private static final String SELECT_PAYMENT_PERIODICALS =
            "SELECT periodical_id FROM payments_periodicals WHERE payment_id = ?;";
    private static final String INSERT_PAYMENT_PERIODICALS =
            "INSERT INTO payments_periodicals(payment_id, periodical_id) VALUES (?,?);";
    private static final String DELETE_PAYMENT_PERIODICALS =
            "DELETE FROM payments_periodicals WHERE payment_id = ?;";
    private static final String SELECT_USER_PAYMENTS_COUNT =
            "SELECT count(*) as count FROM payments WHERE user_id = ?;";

    @Override
    public boolean addPaymentPeriodicals(Payment payment, List<Periodical> subs) throws DaoException {
        return false;
    }

    @Override
    public List<Integer> getPaymentPeriodicals(Long paymentId) throws DaoException {
        return null;
    }

    @Override
    public void deletePaymentPeriodicals(Long paymentId) throws DaoException {

    }

    @Override
    public List<Payment> getUserPaymentsSublist(User user, int take, int skip) {
        return null;
    }

    @Override
    public int getUserPaymentsCount(User user) {
        return 0;
    }

    @Override
    public Long add(Payment element) throws DaoException {
        return null;
    }

    @Override
    public Payment getById(Long key) throws DaoException {
        return null;
    }

    @Override
    public void update(Payment object) throws DaoException {

    }

    @Override
    public void delete(Long key) throws DaoException {

    }

    @Override
    public List<Payment> getAll() throws DaoException {
        return null;
    }

    @Override
    protected Object[] getObjectParams(Payment object) throws DaoException {
        return new Object[0];
    }

    @Override
    protected Object[] getObjectUpdateParams(Payment object) throws DaoException {
        return new Object[0];
    }

    @Override
    protected Long getGeneratedKey(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected List<Payment> parseResultSet(ResultSet rs) throws DaoException {
        return null;
    }

//    @Override
//    public String getSelectQuery() {
//        return "SELECT id, payment_time, payment_sum, user_id FROM payments ";
//    }
//
//    @Override
//    public String getInsertQuery() {
//        return "INSERT INTO payments (payment_sum, user_id) VALUES (?,?);";
//    }
//
//    @Override
//    public String getUpdateQuery() {
//        return "UPDATE payments SET payment_sum = ?, user_id = ? WHERE id = ?;";
//    }
//
//    @Override
//    public String getDeleteQuery() {
//        return "DELETE FROM payments WHERE id = ?;";
//    }
//
//    @Override
//    protected Long getGeneratedKey(ResultSet rs) throws DaoException {
//        try {
//            if (rs.next()) {
//                return rs.getLong(1);
//            }
//            throw new SQLException("entry was not written in DB");
//        } catch (SQLException e) {
//            throw new DaoException("No keys were generated: " + e.getMessage());
//        }
//    }
//
//    @Override
//    protected void setGeneratedKey(Payment pay, Long genId) throws IllegalArgumentException {
//        pay.setId(genId);
//    }
//
//    @Override
//    protected List<Payment> parseResultSet(ResultSet rs) throws DaoException {
//        List<Payment> result = new ArrayList<>();
//        try {
//            while (rs.next()) {
//                Payment pay = new Payment();
//                pay.setId(rs.getLong("id"));
//                pay.setPaymentTime(rs.getTimestamp("payment_time"));
//                pay.setPaymentSum(rs.getBigDecimal("payment_sum"));
//                pay.setUserUuid(rs.getString("user_id"));
//
//                result.add(pay);
//            }
//        } catch (Exception e) {
//            throw new DaoException(e);
//        }
//        return result;
//    }
//
//    @Override
//    protected void prepareStatementForInsert(PreparedStatement stmt, Payment pay) throws DaoException {
//        try {
//            stmt.setBigDecimal(1, pay.getPaymentSum());
//            stmt.setString(2, pay.getUserId());
//        } catch (SQLException | IllegalArgumentException e) {
//            throw new DaoException(e.getMessage());
//        }
//    }
//
//    @Override
//    protected void prepareStatementForUpdate(PreparedStatement statement, Payment object)
//            throws OperationNotSupportedException {
//
//        throw new OperationNotSupportedException();
//    }
//
//    /*TODO decide whether pass only payment*/
//    @Override
//    public boolean addPaymentPeriodicals(Payment payment, List<Periodical> subs) throws DaoException {
//        if (Objects.isNull(payment) || Objects.isNull(payment.getId())) {
//            throw new DaoException("Attempt to add nullable payment periodicals or payment without id");
//        }
//
//        if (Objects.isNull(subs) || subs.size() < 1) {
//            throw new DaoException("Attempt to add payment without periodicals in it");
//        }
//
//        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
//             PreparedStatement stmt = conn.prepareStatement(
//                     INSERT_PAYMENT_PERIODICALS, Statement.RETURN_GENERATED_KEYS)) {
//
//            long payId = payment.getId();
//            for (Periodical sub : subs) {
//                stmt.setLong(1, payId);
//                stmt.setInt(2, sub.getId());
//                stmt.addBatch();
//            }
//            stmt.executeBatch();
//
//            ResultSet rs = stmt.getGeneratedKeys();
//            return rs.getFetchSize() == subs.size();
//        } catch (Exception e) {
//            throw new DaoException(e);
//        }
//    }
//
//    @Override
//    public List<Integer> getPaymentPeriodicals(Long payId) throws DaoException {
//        if (Objects.isNull(payId)) {
//            throw new DaoException("Attempt to get payment periodicals by nullable id");
//        }
//
//        List<Integer> result = new ArrayList<>();
//
//        log.debug("Trying to get objects by payment id");
//        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
//             PreparedStatement stmt = conn.prepareStatement(SELECT_PAYMENT_PERIODICALS)) {
//            stmt.setObject(1, payId);
//
//            log.debug("Trying to parse select query");
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                result.add(rs.getInt("periodical_id"));
//            }
//            return result;
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new DaoException(e);
//        }
//    }
//
//    @Override
//    public void deletePaymentPeriodicals(Long payId) throws DaoException {
//        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
//             PreparedStatement stmt = conn.prepareStatement(DELETE_PAYMENT_PERIODICALS)) {
//
//            stmt.setObject(1, payId);
//            stmt.executeUpdate();
//        } catch (Exception e) {
//            throw new DaoException(e.getMessage());
//        }
//    }
//
//
//    public List<Payment> getUserPaymentsSublist(User user, int skip, int take) throws DaoException {
//        if (skip < 0 || take < 0) {
//            throw new DaoException("skip and take params must be > 0");
//        }
//
//        List<Payment> sublist;
//        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
//             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_PAYMENTS_SUBLIST)) {
//
//            stmt.setString(1, user.getId());
//            stmt.setInt(2, skip);
//            stmt.setInt(3, take);
//
//            ResultSet rs = stmt.executeQuery();
//            sublist = parseResultSet(rs);
//
//            log.debug("Successful object set parsing!");
//            return sublist;
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            throw new DaoException(e);
//        }
//    }
//
//    public int getUserPaymentsCount(User user) throws DaoException {
//        int result = 0;
//        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
//             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_PAYMENTS_COUNT)) {
//
//            stmt.setString(1, user.getId());
//
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                result = rs.getInt("count");
//            }
//            return result;
//        } catch (Exception e) {
//            throw new DaoException(e);
//        }
//    }

//    private class PersistPayment extends Payment {
//        public void setId(long id) {
//            super.setId(id);
//        }
//
//        public void setPaymentTime(Timestamp paymentTime) {
//            super.setPaymentTime(paymentTime);
//        }
//    }
}
