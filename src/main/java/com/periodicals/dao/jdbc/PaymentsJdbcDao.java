package com.periodicals.dao.jdbc;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.interfaces.PaymentsDao;
import com.periodicals.entities.Payment;
import com.periodicals.entities.User;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.JdbcQueriesHolder.*;

public class PaymentsJdbcDao extends AbstractJdbcDao<Payment, UUID> implements PaymentsDao {
    private static final String ID = AttributesPropertyManager.getProperty("payment.id");
    private static final String TIME = AttributesPropertyManager.getProperty("payment.time");
    private static final String SUM = AttributesPropertyManager.getProperty("payment.sum");
    private static final String USER_ID = AttributesPropertyManager.getProperty("payment.user_id");

    private static final UsersJdbcDao usersDao =
            (UsersJdbcDao) JdbcDaoFactory.getInstance().getUsersDao();

    @Override
    public void createEntity(Payment entity) throws DaoException {
        super.insert(PAYMENT_INSERT, getInsertObjectParams(entity));
    }

    @Override
    public void updateEntity(Payment entity) throws DaoException {
        super.update(PAYMENT_UPDATE, getObjectUpdateParams(entity));
    }

    @Override
    public void deleteEntity(Payment entity) throws DaoException {
        super.delete(PAYMENT_DELETE, entity.getId());
    }

    @Override
    public Payment getEntityByPrimaryKey(UUID key) throws DaoException {
        return super.selectObject(PAYMENT_SELECT_BY_ID, key);
    }

    @Override
    public List<Payment> getEntityCollection() throws DaoException {
        return super.selectObjects(PAYMENT_SELECT_ALL);
    }

    @Override
    public List<Payment> getEntitiesListBounded(int skip, int limit) throws DaoException {
        return super.selectObjects(PAYMENT_SELECT_SUBLIST);
    }

    @Override
    public int getEntitiesCount() throws DaoException {
        return super.getEntriesCount(PAYMENT_ENTRIES_COUNT);
    }

    @Override
    public List<Payment> getPaymentsByUserListBounded(int skip, int limit, User user) throws DaoException {
        return super.selectObjects(PAYMENT_SELECT_USER_PAYMENTS_SUBLIST, user.getId(), skip, limit);
    }

    @Override
    public int getPaymentsByUserCount(User user) throws DaoException {
        return super.getEntriesCount(PAYMENT_SELECT_USER_PAYMENTS_COUNT, user.getId());
    }

    @Override
    protected Object[] getInsertObjectParams(Payment payment) {
        return new Object[]{
                payment.getId().toString(),
                payment.getPaymentSum(),
                payment.getUser().getId()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(Payment payment) {
        return new Object[]{
                payment.getPaymentSum(),
                payment.getUser().getId(),
                payment.getId().toString()
        };
    }

    @Override
    protected List<Payment> parseResultSet(ResultSet rs) throws DaoException {
        List<Payment> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Payment payment = new Payment();
                payment.setId(UUID.fromString(rs.getString(ID)));
                payment.setPaymentTime(rs.getTimestamp(TIME));
                payment.setPaymentSum(rs.getBigDecimal(SUM));

                User user = usersDao.getEntityByPrimaryKey(UUID.fromString(rs.getString(USER_ID)));
                payment.setUser(user);

                result.add(payment);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }
}
