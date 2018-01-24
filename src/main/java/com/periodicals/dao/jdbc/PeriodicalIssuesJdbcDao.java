package com.periodicals.dao.jdbc;

import com.periodicals.dao.factories.JdbcDaoFactory;
import com.periodicals.entities.Periodical;
import com.periodicals.dao.interfaces.PeriodicalIssuesDao;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.exceptions.DaoException;
import com.periodicals.utils.propertyManagers.AttributesPropertyManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.periodicals.utils.resourceHolders.JdbcQueriesHolder.*;

public class PeriodicalIssuesJdbcDao extends AbstractJdbcDao<PeriodicalIssue, UUID> implements PeriodicalIssuesDao {
    private static final String ID = AttributesPropertyManager.getProperty("periodical_issue.id");
    private static final String NAME = AttributesPropertyManager.getProperty("periodical_issue.name");
    private static final String ISSUE_NO = AttributesPropertyManager.getProperty("periodical_issue.no");
    private static final String PUBLISHING_DATE = AttributesPropertyManager.getProperty("periodical_issue.publishing_date");
    private static final String PERIODICAL_ID = AttributesPropertyManager.getProperty("periodical_issue.periodical_id");

    private static final PeriodicalsJdbcDao periodicalsDao =
            (PeriodicalsJdbcDao) JdbcDaoFactory.getInstance().getPeriodicalsDao();

    @Override
    public void createEntity(PeriodicalIssue entity) throws DaoException {
        super.insert(PERIODICAL_ISSUE_INSERT, getInsertObjectParams(entity));
    }

    @Override
    public void updateEntity(PeriodicalIssue entity) throws DaoException {
        super.update(PERIODICAL_ISSUE_UPDATE, getObjectUpdateParams(entity));
    }

    @Override
    public void deleteEntity(PeriodicalIssue entity) throws DaoException {
        super.delete(PERIODICAL_ISSUE_DELETE, entity.getId());
    }

    @Override
    public PeriodicalIssue getEntityByPrimaryKey(UUID key) throws DaoException {
        return super.selectObject(PERIODICAL_ISSUE_SELECT_BY_ID, key);
    }

    @Override
    public List<PeriodicalIssue> getEntityCollection() throws DaoException {
        return super.selectObjects(PERIODICAL_ISSUE_SELECT_ALL);
    }

    @Override
    public List<PeriodicalIssue> getEntitiesListBounded(int skip, int limit) throws DaoException {
        return super.selectObjects(PERIODICAL_ISSUE_ALL_SELECT_LIMITED, skip, limit);
    }

    @Override
    public int getEntitiesCount() throws DaoException {
        return super.getEntriesCount(PERIODICAL_ISSUE_ALL_ENTRIES_COUNT);
    }

    @Override
    public List<PeriodicalIssue> getIssuesByPeriodicalListBounded(int skip, int limit, Periodical periodical) throws DaoException {
        return super.selectObjects(PERIODICAL_ISSUE_SELECT_LIMITED, periodical.getId(), skip, limit);
    }

    @Override
    public int getIssuesByPeriodicalCount(Periodical periodical) throws DaoException {
        return super.getEntriesCount(PERIODICAL_ISSUE_PERIODICAL_ENTRIES_COUNT);
    }

    @Override
    protected Object[] getInsertObjectParams(PeriodicalIssue issue) throws DaoException {
        return new Object[]{
                issue.getId(),
                issue.getIssueNo(),
                issue.getName(),
                issue.getPeriodical().getId()
        };
    }

    @Override
    protected Object[] getObjectUpdateParams(PeriodicalIssue issue) throws DaoException {
        return new Object[]{
                issue.getIssueNo(),
                issue.getName(),
                issue.getPeriodical().getId(),
                issue.getId()
        };
    }

    @Override
    protected List<PeriodicalIssue> parseResultSet(ResultSet rs) throws DaoException {
        List<PeriodicalIssue> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PeriodicalIssue issue = new PeriodicalIssue();
                issue.setId(UUID.fromString(rs.getString(ID)));
                issue.setIssueNo(rs.getInt(ISSUE_NO));
                issue.setName(rs.getString(NAME));
                issue.setPublishDate(rs.getDate(PUBLISHING_DATE));

                Periodical periodical = periodicalsDao.
                        getEntityByPrimaryKey(UUID.fromString(rs.getString(PERIODICAL_ID)));

                issue.setPeriodical(periodical);

                result.add(issue);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DaoException(e);
        }
        return result;
    }

//    @Override
//    protected Long getGeneratedKey(ResultSet rs) throws SQLException {
//        return rs.getLong(1);
//    }
}
