package com.periodicals.dao.jdbc;

import com.periodicals.entities.Periodical;
import com.periodicals.dao.interfaces.PeriodicalIssuesDao;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.exceptions.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.periodicals.utils.ResourceHolders.JdbcQueriesHolder.*;

public class PeriodicalIssuesJdbcDao extends AbstractJdbcDao<PeriodicalIssue, Long> implements PeriodicalIssuesDao {

    @Override
    public Long add(PeriodicalIssue issue) throws DaoException {
        return super.insert(PERIODICAL_ISSUE_INSERT, getInsertObjectParams(issue));
    }

    @Override
    public PeriodicalIssue getById(Long id) throws DaoException {
        return super.selectObject(PERIODICAL_ISSUE_SELECT_BY_ID, id);
    }

    @Override
    public void update(PeriodicalIssue issue) throws DaoException {
        super.update(PERIODICAL_ISSUE_UPDATE, getObjectUpdateParams(issue));
    }

    @Override
    public void delete(PeriodicalIssue issue) throws DaoException {
        super.delete(PERIODICAL_ISSUE_DELETE, issue.getId());
    }

    @Override
    public List<PeriodicalIssue> getAll() throws DaoException {
        return super.selectObjects(PERIODICAL_ISSUE_SELECT_ALL);
    }

    @Override
    protected Object[] getInsertObjectParams(PeriodicalIssue issue) throws DaoException {
        Integer issueNo = issue.getIssueNo();
        String name = issue.getName();
        Integer periodicalId = issue.getPeriodicalId();

        return new Object[]{issueNo, name, periodicalId};
    }

    @Override
    protected Object[] getObjectUpdateParams(PeriodicalIssue issue) throws DaoException {
        Long id = issue.getId();
        Integer issueNo = issue.getIssueNo();
        String name = issue.getName();
        Integer periodicalId = issue.getPeriodicalId();

        return new Object[]{issueNo, name, periodicalId, id};
    }

    @Override
    public List<PeriodicalIssue> getPeriodicalIssues(Periodical periodical) throws DaoException {
        return super.selectObjects(PERIODICAL_ISSUE_SELECT_BY_PERIODICAL, periodical.getId());
    }

    @Override
    public long getPeriodicalIssuesCount(Periodical periodical) throws DaoException {
        return super.getEntriesCount(PERIODICAL_ISSUE_PERIODICAL_ENTRIES_COUNT);

    }

    @Override
    public long getAllIssuesCount() throws DaoException {
        return super.getEntriesCount(PERIODICAL_ISSUE_ALL_ENTRIES_COUNT);
    }

    @Override
    public List<PeriodicalIssue> getAllIssuesLimited(long skip, long limit) throws DaoException {
        return super.selectObjects(PERIODICAL_ISSUE_ALL_SELECT_LIMITED, skip, limit);
    }


    @Override
    public List<PeriodicalIssue> getPeriodicalIssuesLimited(Periodical periodical, long skip, long limit) throws DaoException {
        return super.selectObjects(PERIODICAL_ISSUE_SELECT_LIMITED, periodical.getId(), skip, limit);
    }

    @Override
    protected Long getGeneratedKey(ResultSet rs) throws SQLException {
        return rs.getLong(1);
    }

    @Override
    protected List<PeriodicalIssue> parseResultSet(ResultSet rs) throws DaoException {
        List<PeriodicalIssue> result = new ArrayList<>();
        try {
            while (rs.next()) {
                PeriodicalIssue issue = new PeriodicalIssue();
                issue.setId(rs.getLong("id"));
                issue.setIssueNo(rs.getInt("issue_no"));
                issue.setName(rs.getString("name"));
                issue.setPublishDate(rs.getDate("publishing_date"));
                issue.setPeriodicalId(rs.getInt("periodical_id"));

                result.add(issue);
            }
        } catch (SQLException | IllegalArgumentException e) {
            throw new DaoException(e);
        }
        return result;
    }
}
