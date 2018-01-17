package com.periodicals.dao.jdbc;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.entities.*;
import com.periodicals.dao.interfaces.PeriodicalsDao;
import com.periodicals.exceptions.DaoException;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.periodicals.utils.JdbcQueriesHolder.*;

public class PeriodicalsJdbcDao extends AbstractJdbcDao<Periodical, Integer> implements PeriodicalsDao {

    @Override
    protected Integer getGeneratedKey(ResultSet rs) throws SQLException {
        return rs.getInt(1);
    }

    @Override
    public Integer add(Periodical periodical) throws DaoException {
        return super.insert(PERIODICAL_INSERT, getInsertObjectParams(periodical));
    }

    @Override
    public Periodical getById(Integer id) throws DaoException {
        return super.selectObject(PERIODICAL_SELECT_BY_ID, id);
    }

    @Override
    public void update(Periodical periodical) throws DaoException {
        super.update(PERIODICAL_UPDATE, getObjectUpdateParams(periodical));
    }

    @Override
    public void delete(Integer key) throws DaoException {
        super.delete(PERIODICAL_DELETE, key);
    }

    @Override
    public List<Periodical> getAll() throws DaoException {
        return super.selectObjects(PERIODICAL_SELECT_ALL);
    }

    @Override
    public List<Periodical> getUserSubscriptions(User user) throws DaoException {
        return super.selectObjects(SUBSCRIPTIONS_SELECT_USER_SUBSCRIPTIONS, user.getId());
    }

    @Override
    public List<Periodical> getGenrePeriodicalsSublist(Genre genre, int skip, int take) throws DaoException {
        return super.selectObjects(PERIODICAL_SELECT_SUBLIST_BY_GENRE, genre.getId(), skip, take);
    }

    @Override
    public long getGenrePeriodicalCount(Genre genre) throws DaoException {
        return super.getEntriesCount(PERIODICAL_ENTRIES_BY_GENRE_COUNT, genre.getId());
    }

    @Override
    public List<Periodical> getPeriodicalSubList(int skip, int take) throws DaoException {
        return super.selectObjects(PERIODICAL_SELECT_SUBLIST, skip, take);
    }

    @Override
    public long getPeriodicalsCount() throws DaoException {
        return super.getEntriesCount(PERIODICAL_ENTRIES_COUNT);
    }

    @Override
    public long getUserSubscriptionsCount(User user) throws DaoException {
        return super.getEntriesCount(SUBSCRIPTIONS_USER_SUBSCRIPTIONS_COUNT, user.getId());
    }

    @Override
    public boolean isUserSubscribed(User user, Periodical per) throws DaoException {
        long count = super.getEntriesCount(SUBSCRIPTIONS_IS_USER_SUBSCRIBED, per.getId(), user.getId());
        return count > 0;
    }

    @Override
    public List<Periodical> getPaymentPeriodicals(Payment payment) throws DaoException {
        return super.selectObjects(SELECT_PAYMENT_PERIODICALS, payment.getId());
    }

    /*TODO think of how to make generic*/
    @Override
    public void addUserSubscriptions(User user, List<Periodical> subs) throws DaoException {
        if (Objects.isNull(user) || Objects.isNull(user.getId())) {
            throw new DaoException("Attempt to add subscriptions to nullable user or with empty id.");
        }
        if (Objects.isNull(subs) || subs.size() < 1) {
            throw new DaoException("Attempt to add subs without periodicals");
        }

        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(SUBSCRIPTIONS_ADD_USER_SUBSCRIPTION)) {

            for (Periodical sub : subs) {
                stmt.setString(1, user.getId());
                stmt.setInt(2, sub.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected Object[] getInsertObjectParams(Periodical periodical) throws DaoException {
        String name = periodical.getName();
        String description = periodical.getDescription();
        Short issuesPerYear = periodical.getIssuesPerYear();
        BigDecimal subscriptionCost = periodical.getSubscriptionCost();
        Boolean isLimited = periodical.getIsLimited();
        Short genreId = periodical.getGenre().getId();
        Integer publisherId = periodical.getPublisher().getId();

        return new Object[]{name, description, subscriptionCost, issuesPerYear, isLimited, genreId, publisherId};
    }

    @Override
    protected Object[] getObjectUpdateParams(Periodical periodical) throws DaoException {
        Integer id = periodical.getId();
        String name = periodical.getName();
        String description = periodical.getDescription();
        Short issuesPerYear = periodical.getIssuesPerYear();
        BigDecimal subscriptionCost = periodical.getSubscriptionCost();
        Boolean isLimited = periodical.getIsLimited();
        Short genreId = periodical.getGenre().getId();
        Integer publisherId = periodical.getPublisher().getId();

        return new Object[]{name, description, subscriptionCost, issuesPerYear, isLimited, genreId, publisherId, id};
    }

    @Override
    protected List<Periodical> parseResultSet(ResultSet rs) throws DaoException {
        List<Periodical> result = new ArrayList<>();
        try {
            while (rs.next()) {
                Periodical per = new Periodical();
                per.setId(rs.getInt("id"));
                per.setName(rs.getString("name"));
                per.setDescription(rs.getString("description"));
                per.setSubscriptionCost(rs.getBigDecimal("subscr_cost"));
                per.setIssuesPerYear(rs.getShort("issues_per_year"));
                per.setLimited(rs.getBoolean("is_limited"));

                Genre genre = new Genre();
                genre.setId(rs.getShort("genre_id"));
                genre.setName(rs.getString("genre_name"));
                per.setGenre(genre);

                Publisher publisher = new Publisher();
                publisher.setId(rs.getInt("publisher_id"));
                publisher.setName(rs.getString("publisher_name"));
                per.setPublisher(publisher);

                result.add(per);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }
}
