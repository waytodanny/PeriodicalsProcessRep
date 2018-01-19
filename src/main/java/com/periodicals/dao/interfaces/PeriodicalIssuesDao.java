package com.periodicals.dao.interfaces;

import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.exceptions.DaoException;

import java.util.List;

public interface PeriodicalIssuesDao extends GenericDao<PeriodicalIssue, Long> {
    List<PeriodicalIssue> getIssuesByPeriodical(Periodical periodical) throws DaoException;

    long getPeriodicalIssuesCount(Periodical periodical) throws DaoException;

    long getAllIssuesCount() throws DaoException;

    List<PeriodicalIssue> getAllIssuesLimited(long skip, long limit) throws DaoException;

    List<PeriodicalIssue> getPeriodicalIssuesLimited(Periodical periodical, long skip, long limit) throws DaoException;
}
