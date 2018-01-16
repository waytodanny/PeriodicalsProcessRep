package com.periodicals.dao.jdbc;

import com.periodicals.dao.connection.ConnectionManager;
import com.periodicals.dao.connection.ConnectionWrapper;
import com.periodicals.dao.interfaces.PeriodicalIssuesDao;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.exceptions.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeriodicalIssuesJdbcDao extends AbstractJdbcDao<PeriodicalIssue, Long> implements PeriodicalIssuesDao {

    @Override
    public String getSelectQuery() {
        return "SELECT id, issue_no, name, publishing_date, periodical_id FROM periodical_issues ";
    }

    /*TODO change now by Java now()*/
    @Override
    public String getInsertQuery() {
        return "INSERT INTO periodical_issues(issue_no, name, periodical_id, publishing_date)" +
                "VALUES (?,?,?,NOW());";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE periodical_issues SET issue_no = ?, name = ?, periodical_id = ? WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM periodical_issues WHERE id = ?;";
    }

    @Override
    public Long getGeneratedKey(ResultSet rs) throws DaoException {
        try {
            if (rs.next())
                return rs.getLong(1);

            throw new SQLException("entry was not written in DB");
        } catch (SQLException e) {
            throw new DaoException("No keys were generated: " + e.getMessage());
        }
    }

    @Override
    protected void setGeneratedKey(PeriodicalIssue object, Long genId) throws IllegalArgumentException {
        object.setId(genId);
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

    @Override
    protected void prepareStatementForInsert(PreparedStatement stmt, PeriodicalIssue object) throws DaoException {
        try {
            stmt.setInt(1, object.getIssueNo());
            stmt.setString(2, object.getName());
            stmt.setLong(3, object.getPeriodicalId());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stmt, PeriodicalIssue object) throws DaoException {
        try {
            stmt.setInt(1, object.getIssueNo());
            stmt.setString(2, object.getName());
            stmt.setLong(3, object.getPeriodicalId());
            stmt.setLong(4, object.getId());
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<PeriodicalIssue> getIssuesByPeriodicalId(int id) throws DaoException {
        if (id < 1) {
            log.error("Attempt to get issue by negative id value");
            throw new DaoException("Attempt to get issue by negative id value");
        }

        List<PeriodicalIssue> issues;
        String sqlQuery = getSelectQuery() +
                "WHERE  periodical_id = ?";

        try (ConnectionWrapper conn = ConnectionManager.getConnectionWrapper();
             PreparedStatement stmt = conn.prepareStatement(sqlQuery)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            issues = parseResultSet(rs);

            log.debug("Successful object set parsing!");
            return issues;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new DaoException(e);
        }
    }

//    private class PersistPeriodicalIssue extends PeriodicalIssue {
//        public void setId(int id) {
//            super.setId(id);
//        }
//
//        public void setPublishDate(Date publishDate) {
//            super.setPublishDate(publishDate);
//        }
//    }
}
