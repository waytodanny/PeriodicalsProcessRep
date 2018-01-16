package com.periodicals.dao.jdbc;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.dao.interfaces.PeriodicalsDao;
import com.periodicals.entities.Genre;
import com.periodicals.entities.Payment;
import com.periodicals.entities.Periodical;
import com.periodicals.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PeriodicalsJdbcDao extends AbstractJdbcDao<Periodical, Integer> implements PeriodicalsDao {
    public static final String IS_USER_SUBSCRIBED = "SELECT count(*) as count FROM subscriptions WHERE periodical_id = ? AND user_id = ?;";

    private static final String SELECT_ENTRIES_COUNT =
            "SELECT COUNT(*) AS count FROM periodicals";
    private static final String ADD_USER_SUBSCRIPTION =
            "INSERT INTO subscriptions(user_id, periodical_id) VALUES (?,?);";
    private static final String SELECT_PERIODICALS_SUBLIST_BY_GENRE =
            "SELECT id, name, description, subscr_cost, issues_per_year, is_limited, genre_id, publisher_id " +
                    "FROM periodicals WHERE genre_id = ? LIMIT ?, ?;";
    private static final String SELECT_GENRE_ENTRIES_COUNT =
            "SELECT COUNT(*) AS count FROM periodicals WHERE genre_id = ?;";
    private static final String SELECT_USER_SUBSCRIPTIONS_COUNT =
            "SELECT COUNT(*) AS count FROM subscriptions WHERE user_id = ?;";
    public static final String SELECT_PAYMENT_PERIODICALS =
            "SELECT * FROM periodicals AS p JOIN payments_periodicals AS pp ON p.id = pp.periodical_id HAVING payment_id = ?;";

    @Override
    public String getSelectQuery() {
        return "SELECT id, name, description, subscr_cost, issues_per_year, is_limited, genre_id, publisher_id " +
                "FROM periodicals ";
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO periodicals(name, description, subscr_cost, issues_per_year, is_limited, genre_id, publisher_id) " +
                "VALUES (?,?,?,?,?,?,?)";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE periodicals SET name = ?, description = ?, subscr_cost = ?, issues_per_year = ?, " +
                "is_limited = ?, genre_id = ?, publisher_id = ? WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM periodicals WHERE id = ?;";
    }

    @Override
    public Integer getGeneratedKey(ResultSet rs) throws DaoException {
        try {
            if (rs.next())
                return rs.getInt(1);

            throw new SQLException("entry was not written in DB");
        } catch (SQLException e) {
            throw new DaoException("No keys were generated: " + e.getMessage());
        }
    }

    @Override
    protected void setGeneratedKey(Periodical periodical, Integer genId) throws IllegalArgumentException {
        periodical.setId(genId);
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
                per.setGenreId(rs.getShort("genre_id"));
                per.setPublisherId(rs.getInt("publisher_id"));

                result.add(per);
            }
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stmt, Periodical per) throws DaoException {
        try {
            stmt.setString(1, per.getName());
            stmt.setString(2, per.getDescription());
            stmt.setBigDecimal(3, per.getSubscriptionCost());
            stmt.setShort(4, per.getIssuesPerYear());
            stmt.setBoolean(5, per.isLimited());
            stmt.setShort(6, per.getGenreId());
            stmt.setInt(7, per.getPublisherId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stmt, Periodical per) throws DaoException {
        try {
            stmt.setString(1, per.getName());
            stmt.setString(2, per.getDescription());
            stmt.setBigDecimal(3, per.getSubscriptionCost());
            stmt.setShort(4, per.getIssuesPerYear());
            stmt.setBoolean(5, per.isLimited());
            stmt.setShort(6, per.getGenreId());
            stmt.setInt(7, per.getPublisherId());
            stmt.setInt(8, per.getId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean isUserSubscribed(String id, Periodical per) throws DaoException {
        if (Objects.isNull(id)) {
            log.error("Attempt to get subscriptions of nullable id.");
            throw new DaoException("Attempt to get subscriptions of nullable id.");
        }

        List<Periodical> subscriptions;
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(IS_USER_SUBSCRIBED)) {

            stmt.setInt(1, per.getId());
            stmt.setString(2, id);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("count") > 0;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public List<Periodical> getUserSubscriptions(String userUuid) throws DaoException {
        if (Objects.isNull(userUuid)) {
            log.error("Attempt to get subscriptions of nullable id.");
            throw new DaoException("Attempt to get subscriptions of nullable id.");
        }

        List<Periodical> subscriptions;
        String sqlQuery = getSelectQuery() +
                "WHERE id IN (SELECT periodical_id FROM subscriptions WHERE user_id = ?)";

        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            stmt.setString(1, userUuid);

            ResultSet rs = stmt.executeQuery();

            log.debug("Got result set by query, trying to parse it...");
            subscriptions = parseResultSet(rs);

            log.debug("Successful object set parsing!");
            return subscriptions;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public void addUserSubscriptions(String id, List<Periodical> subs) throws DaoException {
        if (Objects.isNull(id)) {
            log.error("Attempt to add subscriptions to nullable user or with empty id.");
            throw new DaoException("Attempt to add subscriptions to nullable user or with empty id.");
        }
        if (Objects.isNull(subs) || subs.size() < 1) {
            throw new DaoException("Attempt to add subs without periodicals");
        }

        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(ADD_USER_SUBSCRIPTION)) {

            for (Periodical sub : subs) {
                stmt.setString(1, id);
                stmt.setInt(2, sub.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public List<Periodical> getGenrePeriodicalsSublist(Genre genre, int skip, int take) throws DaoException {
        if (Objects.isNull(genre)) {
            log.error("passed nullable genre");
            throw new DaoException("passed nullable genre");
        }
        if (skip < 0 && take < 0) {
            log.error("skip and take params must be > 0");
            throw new DaoException("skip and take params must be > 0");
        }

        List<Periodical> result;
        String sqlQuery = SELECT_PERIODICALS_SUBLIST_BY_GENRE;
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            stmt.setShort(1, genre.getId());
            stmt.setInt(2, skip);
            stmt.setInt(3, take);

            ResultSet rs = stmt.executeQuery();
            result = parseResultSet(rs);

            log.debug("Successful object set parsing!");
            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public List<Periodical> getPeriodicalSubList(int skip, int take) throws DaoException {
        if (skip < 0 || take < 0) {
            throw new DaoException("skip and take params must be > 0");
        }

        List<Periodical> sublist;
        String sqlQuery = getSelectQuery() +
                "limit ?,?;";

        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {

            stmt.setInt(1, skip);
            stmt.setInt(2, take);

            ResultSet rs = stmt.executeQuery();
            sublist = parseResultSet(rs);

            log.debug("Successful object set parsing!");
            return sublist;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public int getPeriodicalsCount() throws DaoException {
        int result = 0;

        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ENTRIES_COUNT)) {

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("count");
            }

            return result;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public int getUserSubscriptionsCount(String userId) throws DaoException {
        int result = 0;
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(SELECT_USER_SUBSCRIPTIONS_COUNT)) {
            stmt.setString(1, userId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("count");
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Periodical> getPaymentPeriodicals(Payment payment) throws DaoException {
        if (Objects.isNull(payment)) {
            log.error("Attempt to get pers of nullable payment.");
            throw new DaoException("Attempt to get pers of nullable payment");
        }

        List<Periodical> periodicals;
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(SELECT_PAYMENT_PERIODICALS)) {

            stmt.setLong(1, payment.getId());

            ResultSet rs = stmt.executeQuery();

            log.debug("Got result set by query, trying to parse it...");
            periodicals = parseResultSet(rs);

            log.debug("Successful object set parsing!");
            return periodicals;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public int getGenrePeriodicalCount(Genre genre) throws DaoException {
        int result = 0;
        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(SELECT_GENRE_ENTRIES_COUNT)) {
            stmt.setString(1, genre.getName());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("count");
            }
            return result;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

//    private class PersistPeriodical extends PeriodicalDto {
//        public void setId(int id) {
//            super.setId(id);
//        }
//    }
}
