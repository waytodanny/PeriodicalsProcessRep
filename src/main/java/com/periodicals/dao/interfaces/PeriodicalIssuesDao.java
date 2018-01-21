package com.periodicals.dao.interfaces;

import com.periodicals.entities.Periodical;
import com.periodicals.entities.PeriodicalIssue;
import com.periodicals.exceptions.DaoException;

import java.util.List;

public interface PeriodicalIssuesDao extends GenericDao<PeriodicalIssue, String> {
    List<PeriodicalIssue> getPeriodicalIssues(Periodical periodical) throws DaoException;

    int getPeriodicalIssuesCount(Periodical periodical) throws DaoException;

    int getAllIssuesCount() throws DaoException;

    List<PeriodicalIssue> getAllIssuesLimited(int skip, int limit) throws DaoException;

    List<PeriodicalIssue> getPeriodicalIssuesLimited(Periodical periodical, int skip, int limit) throws DaoException;
}
