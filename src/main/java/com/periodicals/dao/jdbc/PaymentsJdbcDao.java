package com.periodicals.dao.jdbc;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.dao.interfaces.PaymentsDao;
import com.periodicals.dao.entities.Payment;
import com.periodicals.dao.entities.Periodical;
import com.periodicals.dao.entities.User;
import com.periodicals.exceptions.DaoException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.periodicals.utils.JdbcQueriesHolder.*;

public class PaymentsJdbcDao extends AbstractJdbcDao<Payment, Long> implements PaymentsDao {

    @Override
    public Long add(Payment payment) throws DaoException {
        return super.insert(PAYMENT_INSERT, getInsertObjectParams(payment));
    }

    @Override
    public Payment getById(Long id) throws DaoException {
        return super.selectObject(PAYMENT_SELECT_BY_ID, id);
    }

    @Override
    public void update(Payment payment) throws DaoException {
        super.update(PAYMENT_UPDATE, getObjectUpdateParams(payment));
    }

    @Override
    public void delete(Long id) throws DaoException {
        super.delete(PAYMENT_DELETE, id);
    }

    @Override
    public List<Payment> getAll() throws DaoException {
        return super.selectObjects(PAYMENT_SELECT_ALL);
    }

    @Override
    public void deletePaymentPeriodicals(Long paymentId) throws DaoException {
        super.delete(PAYMENT_DELETE_PAYMENT_PERIODICALS, paymentId);
    }

    @Override
    public List<Payment> getUserPaymentsSublist(User user, int skip, int take) throws DaoException {
        return super.selectObjects(PAYMENT_SELECT_USER_PAYMENTS_SUBLIST, user.getId(), skip, take);
    }

    @Override
    public long getUserPaymentsCount(User user) throws DaoException {
        return super.getEntriesCount(PAYMENT_SELECT_USER_PAYMENTS_COUNT, user.getId());
    }

    /*TODO make generic*/
    @Override
    public void addPaymentPeriodicals(Payment payment) throws DaoException {
        if (Objects.isNull(payment) || Objects.isNull(payment.getId())
                || Objects.isNull(payment.getPeriodicals()) || (payment.getPeriodicals().size() < 1)) {
            throw new DaoException("Attempt to add nullable payment periodicals or payment without id");
        }

        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(PAYMENT_INSERT_PAYMENT_PERIODICALS)) {

            long payId = payment.getId();
            for (Periodical sub : payment.getPeriodicals()) {
                stmt.setLong(1, payId);
                stmt.setInt(2, sub.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected Object[] getInsertObjectParams(Payment payment) throws DaoException {
        BigDecimal paymentSum = payment.getPaymentSum();
        String userId = payment.getUserId();

        return new Object[]{paymentSum, userId};
    }

    @Override
    protected Object[] getObjectUpdateParams(Payment payment) throws DaoException {
        Long id = payment.getId();
        BigDecimal paymentSum = payment.getPaymentSum();
        String userId = payment.getUserId();

        return new Object[]{paymentSum, userId, id};
    }

    @Override
    protected Long getGeneratedKey(ResultSet rs) throws SQLException {
        return rs.getLong(1);
    }

    @Override
    protected List<Payment> parseResultSet(ResultSet rs) throws DaoException {
        List<Payment> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Payment pay = new Payment();
                pay.setId(rs.getLong("id"));
                pay.setPaymentTime(rs.getTimestamp("payment_time"));
                pay.setPaymentSum(rs.getBigDecimal("payment_sum"));
                pay.setUserId(rs.getString("user_id"));

                result.add(pay);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
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
//                pay.setUserId(rs.getString("user_id"));
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
