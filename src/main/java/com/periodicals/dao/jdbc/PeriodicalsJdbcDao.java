package com.periodicals.dao.jdbc;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.dao.interfaces.PeriodicalsDao;
import com.periodicals.entities.*;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.JdbcQueriesHolder.*;

public class PeriodicalsJdbcDao extends AbstractJdbcDao<Periodical, UUID> implements PeriodicalsDao {
    private static final String ID = AttributesPropertyManager.getProperty("periodical.id");
    private static final String NAME = AttributesPropertyManager.getProperty("periodical.name");
    private static final String DESCRIPTION = AttributesPropertyManager.getProperty("periodical.description");
    private static final String SUBSCRIPTION_COST = AttributesPropertyManager.getProperty("periodical.cost");
    private static final String ISSUES_PER_YEAR = AttributesPropertyManager.getProperty("periodical.issues_per_year");
    private static final String IS_LIMITED = AttributesPropertyManager.getProperty("periodical.is_limited");
    private static final String GENRE_ID = AttributesPropertyManager.getProperty("periodical.genre.id");
    private static final String PUBLISHER_ID = AttributesPropertyManager.getProperty("periodical.publisher.id");

    private static final GenresJdbcDao genresDao =
            (GenresJdbcDao) JdbcDaoFactory.getInstance().getGenresDao();
    private static final PublishersJdbcDao publishersDao =
            (PublishersJdbcDao) JdbcDaoFactory.getInstance().getPublishersDao();

    @Override
    public void createEntity(Periodical entity) throws DaoException {
        super.insert(PERIODICAL_INSERT, getInsertObjectParams(entity));
    }

    @Override
    public Periodical getEntityByPrimaryKey(UUID key) throws DaoException {
        return super.selectObject(PERIODICAL_SELECT_BY_ID, key.toString());
    }

    @Override
    public void updateEntity(Periodical periodical) throws DaoException {
        super.update(PERIODICAL_UPDATE, getObjectUpdateParams(periodical));
    }

    @Override
    public void deleteEntity(Periodical entity) throws DaoException {
        super.delete(PERIODICAL_DELETE, entity.getId());
    }

    @Override
    public List<Periodical> getEntityCollection() throws DaoException {
        return super.selectObjects(PERIODICAL_SELECT_ALL);
    }

    @Override
    public int getEntitiesCount() throws DaoException {
        return super.getEntriesCount(PERIODICAL_ENTRIES_COUNT);
    }

    @Override
    public List<Periodical> getPeriodicalsByGenreListBounded(int skip, int limit, Genre genre) throws DaoException {
        return super.selectObjects(PERIODICAL_SELECT_SUBLIST_BY_GENRE, genre.getId(), skip, limit);
    }

    @Override
    public int getPeriodicalsByGenreCount(Genre genre) throws DaoException {
        return super.getEntriesCount(PERIODICAL_ENTRIES_BY_GENRE_COUNT, genre.getId());
    }

    @Override
    public List<Periodical> getPeriodicalsByPaymentList(Payment payment) throws DaoException {
        return super.selectObjects(SELECT_PAYMENT_PERIODICALS, payment.getId());
    }

    @Override
    public List<Periodical> getEntitiesListBounded(int skip, int take) throws DaoException {
        return super.selectObjects(PERIODICAL_SELECT_SUBLIST, skip, take);
    }

    @Override
    public List<Periodical> getPeriodicalsByUserList(User user) throws DaoException {
        return super.selectObjects(SUBSCRIPTIONS_SELECT_USER_SUBSCRIPTIONS, user.getId());
    }

    @Override
    public List<Periodical> getPeriodicalsByUserListBounded(int skip, int limit, User user) throws DaoException {
        return super.selectObjects(SUBSCRIPTIONS_SELECT_USER_SUBSCRIPTIONS_LIMIT, skip, limit);
    }

    @Override
    public int getPeriodicalsByUserCount(User user) throws DaoException {
        return super.getEntriesCount(SUBSCRIPTIONS_USER_SUBSCRIPTIONS_COUNT, user.getId());
    }

    @Override
    public boolean getIsUserSubscribedOnPeriodical(User user, Periodical per) throws DaoException {
        int count = super.getEntriesCount(SUBSCRIPTIONS_IS_USER_SUBSCRIBED, per.getId(), user.getId());
        return count > 0;
    }

//    /*TODO think of how to make generic*/
//    @Override
//    public void addUserSubscriptions(User user, List<Periodical> subs) throws DaoException {
//        if (Objects.isNull(user) || Objects.isNull(user.getId())) {
//            throw new DaoException("Attempt to addNewIssue subscriptions to nullable user or with empty id.");
//        }
//
//        if (Objects.isNull(subs) || subs.size() < 1) {
//            throw new DaoException("Attempt to addNewIssue subs without periodicals");
//        }
//
//        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
//             PreparedStatement stmt = conn.prepareStatement(SUBSCRIPTIONS_ADD_USER_SUBSCRIPTION)) {
//
//            for (Periodical sub : subs) {
//                stmt.setString(1, user.getId().toString());
//                stmt.setString(2, sub.getId().toString());
//                stmt.addBatch();
//            }
//            stmt.executeBatch();
//        } catch (Exception e) {
//            throw new DaoException(e);
//        }
//    }

    @Override
    protected Object[] getInsertObjectParams(Periodical periodical) throws DaoException {
        return new Object[]{
                periodical.getId(),
                periodical.getName(),
                periodical.getDescription(),
                periodical.getSubscriptionCost(),
                periodical.getIssuesPerYear(),
                periodical.getIsLimited(),
                periodical.getGenre().getId(),
                periodical.getPublisher().getId()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(Periodical periodical) throws DaoException {
        return new Object[]{
                periodical.getName(),
                periodical.getDescription(),
                periodical.getSubscriptionCost(),
                periodical.getIssuesPerYear(),
                periodical.getIsLimited(),
                periodical.getGenre().getId(),
                periodical.getPublisher().getId(),
                periodical.getId()
        };
    }

    @Override
    protected List<Periodical> parseResultSet(ResultSet rs) throws DaoException {
        List<Periodical> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Periodical per = new Periodical();
                per.setId(UUID.fromString(rs.getString(ID)));
                per.setName(rs.getString(NAME));
                per.setDescription(rs.getString(DESCRIPTION));
                per.setSubscriptionCost(rs.getBigDecimal(SUBSCRIPTION_COST));
                per.setIssuesPerYear(rs.getShort(ISSUES_PER_YEAR));
                per.setLimited(rs.getBoolean(IS_LIMITED));

                Genre genre = genresDao.getEntityByPrimaryKey(UUID.fromString(rs.getString(GENRE_ID)));
                per.setGenre(genre);

                Publisher publisher = publishersDao.getEntityByPrimaryKey(UUID.fromString(rs.getString(PUBLISHER_ID)));
                per.setPublisher(publisher);

                result.add(per);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }

//    @Override
//    protected Integer getGeneratedKey(ResultSet rs) throws SQLException {
//        return rs.getInt(1);
//    }
}
