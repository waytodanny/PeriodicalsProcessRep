package com.periodicals.dao.jdbc;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.dao.interfaces.PaymentsDao;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.periodicals.utils.ResourceHolders.JdbcQueriesHolder.*;

public class PaymentsJdbcDao extends AbstractJdbcDao<Payment, String> implements PaymentsDao {
    private static final String ID = AttributesPropertyManager.getProperty("payment.id");
    private static final String TIME = AttributesPropertyManager.getProperty("payment.time");
    private static final String SUM = AttributesPropertyManager.getProperty("payment.sum");
    private static final String USER_ID = AttributesPropertyManager.getProperty("payment.user_id");

    @Override
    public void add(Payment payment) throws DaoException {
        super.insert(PAYMENT_INSERT, getInsertObjectParams(payment));
    }

    @Override
    public Payment getById(String id) throws DaoException {
        return super.selectObject(PAYMENT_SELECT_BY_ID, id);
    }

    @Override
    public void update(Payment payment) throws DaoException {
        super.update(PAYMENT_UPDATE, getObjectUpdateParams(payment));
    }

    @Override
    public void delete(String id) throws DaoException {
        super.delete(PAYMENT_DELETE, id);
    }

    @Override
    public List<Payment> getAll() throws DaoException {
        return super.selectObjects(PAYMENT_SELECT_ALL);
    }

    @Override
    public void deletePaymentPeriodicals(Payment payment) throws DaoException {
        super.delete(PAYMENT_DELETE_PAYMENT_PERIODICALS, payment.getId());
    }

    @Override
    public List<Payment> getUserPaymentsSublist(User user, int skip, int limit) throws DaoException {
        return super.selectObjects(PAYMENT_SELECT_USER_PAYMENTS_SUBLIST, user.getId(), skip, limit);
    }

    @Override
    public int getUserPaymentsCount(User user) throws DaoException {
        return super.getEntriesCount(PAYMENT_SELECT_USER_PAYMENTS_COUNT, user.getId());
    }

    /*TODO make generic*/
    @Override
    public void addPaymentPeriodicals(Payment payment, List<Periodical> periodicals) throws DaoException {
        if (Objects.isNull(payment) ||
                Objects.isNull(periodicals) ||
                (payment.getPeriodicals().size() < 1)) {
            throw new DaoException("Attempt to add nullable payment periodicals or payment without id");
        }

        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(PAYMENT_INSERT_PAYMENT_PERIODICALS)) {

            String paymentId = payment.getId();
            for (Periodical subscription : periodicals) {
                stmt.setString(1, paymentId);
                stmt.setString(2, subscription.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected Object[] getInsertObjectParams(Payment payment) {
        return new Object[]{
                payment.getId(),
                payment.getPaymentSum(),
                payment.getUserId()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(Payment payment) {
        return new Object[]{
                payment.getPaymentSum(),
                payment.getUserId(),
                payment.getId()
        };
    }

    @Override
    protected List<Payment> parseResultSet(ResultSet rs) throws DaoException {
        List<Payment> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Payment pay = new Payment();
                pay.setId(rs.getString(ID));
                pay.setPaymentTime(rs.getTimestamp(TIME));
                pay.setPaymentSum(rs.getBigDecimal(SUM));
                pay.setUserId(rs.getString(USER_ID));

                result.add(pay);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }

//    @Override
//    protected Long getGeneratedKey(ResultSet rs) throws SQLException {
//        return rs.getLong(1);
//    }
}
